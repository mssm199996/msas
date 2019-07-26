package com.msas.MSAS.IONotifiars;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;
import com.msas.MSAS.DomainModel.Signal.Intrusion;
import com.msas.MSAS.DomainModel.Signal.Notification;
import com.msas.MSAS.DomainModel.Signal.Notification.TypeNotification;
import com.msas.MSAS.Repositories.IntrusionRepository;
import com.msas.MSAS.Repositories.PersonnelSurveillanceRepository;
import com.msas.MSAS.SallePersonnelMqttHandlers.SalleHandler;
import com.msas.MSAS.email.EmailService;
import com.msas.MSAS.gsm.GSMModemServices;
import com.msas.MSAS.mqtt.MsasMqttResponsesDictionary;
import com.msas.MSAS.mqtt.MsasMqttTopicsDictionary;

@Service
public class IntrusionNotifiar extends SalleHandler {

	@Autowired
	private PersonnelSurveillanceRepository personnelSurveillanceRepository;

	@Autowired
	private IntrusionRepository intrusionRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private GSMModemServices gsmModemServices;

	public IntrusionNotifiar() throws MqttException {
		super();
	}

	@Override
	protected int payloadAsArraySupposedSize() {
		return 1;
	}

	@Override
	protected MsasMqttTopicsDictionary topicToSubscribeTo() {
		return MsasMqttTopicsDictionary.MSAS_INTRUSION_NOTIFICATIONS_TOPIC;
	}

	@Override
	protected MsasMqttTopicsDictionary onSalleNotFoundTopic() {
		return MsasMqttTopicsDictionary.MSAS_INTRUSION_NOTIFICATIONS_ERROR_TOPIC;
	}

	@Override
	protected void onSalleFound(Salle salle, String materialId,
			String[] wholeParams) throws MqttPersistenceException,
			MqttException {

		List<PersonnelSurveillance> personnelsSurveillances = this.personnelSurveillanceRepository
				.findBySalles(salle);

		Intrusion intrusion = new Intrusion();
		intrusion.setDate(LocalDate.now());
		intrusion.setHeure(LocalTime.now());
		intrusion.setSalle(salle);

		if (!personnelsSurveillances.isEmpty()) {
			this.publish(
					MsasMqttTopicsDictionary.MSAS_INTRUSION_NOTIFICATIONS_SUCCESS_TOPIC,
					MsasMqttResponsesDictionary.MSAS_NOTIFICATION_ACKNOWLEDGED,
					materialId);

			for (PersonnelSurveillance personnelSurveillance : personnelsSurveillances) {
				this.notifyToPersonnelSurveillance(personnelSurveillance,
						intrusion);
			}

			this.intrusionRepository.save(intrusion);
		} else {
			this.publish(
					MsasMqttTopicsDictionary.MSAS_INTRUSION_NOTIFICATIONS_ERROR_TOPIC,
					MsasMqttResponsesDictionary.MSAS_UNAFFECTED_SALLE_TO_PERSONNEL_SURVEILLANCE,
					materialId);
		}
	}

	private void notifyToPersonnelSurveillance(
			PersonnelSurveillance personnelSurveillance, Intrusion intrusion) {

		String messageSubject = "Intrusion à la salle "
				+ intrusion.getSalle().getDesignation();

		String messageContent = "Une intrusion a été détectée à la salle "
				+ intrusion.getSalle().getDesignation()
				+ " le "
				+ intrusion.getDate().format(
						DateTimeFormatter.ofPattern("dd MMM yyyy"))
				+ " à "
				+ intrusion.getHeure().format(
						DateTimeFormatter.ofPattern("hh:mm:ss"));

		String email = personnelSurveillance.getEmail();
		String numeroTelephone = personnelSurveillance.getNumeroTelephone();

		if (email != null && !email.equals("")) {
			Notification notification = new Notification();
			notification.setTypeNotification(TypeNotification.EMAIL);
			notification.setPersonnelSurveillance(personnelSurveillance);

			intrusion.addNotification(notification);

			(new Thread(() -> {
				this.emailService.sendSimpleMessage(email, messageSubject,
						messageContent);
			})).start();
		}

		if (numeroTelephone != null && !numeroTelephone.equals("")) {
			Notification notification = new Notification();
			notification.setTypeNotification(TypeNotification.SMS);
			notification.setPersonnelSurveillance(personnelSurveillance);

			intrusion.addNotification(notification);

			(new Thread(() -> {
				this.gsmModemServices.sendIntrusionSms(intrusion,
						personnelSurveillance);
			})).start();
		}
	}
}