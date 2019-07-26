package com.msas.MSAS.IONotifiars;

import java.time.LocalDate;
import java.time.LocalTime;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msas.MSAS.DomainModel.Access.AccesReel;
import com.msas.MSAS.DomainModel.Access.AccesReel.TypeAcces;
import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Personnel.Personnel;
import com.msas.MSAS.Repositories.AccesReelRepository;
import com.msas.MSAS.Repositories.DroitRepository;
import com.msas.MSAS.SallePersonnelMqttHandlers.SallePersonnelHandler;
import com.msas.MSAS.mqtt.MsasMqttResponsesDictionary;
import com.msas.MSAS.mqtt.MsasMqttTopicsDictionary;

@Service
public class IONotifiar extends SallePersonnelHandler {

	@Autowired
	private AccesReelRepository accesReelRepository;

	@Autowired
	private DroitRepository droitRepository;

	public IONotifiar() throws MqttException {
		super();
	}

	@Override
	protected int payloadAsArraySupposedSize() {
		return 3;
	}

	@Override
	protected MsasMqttTopicsDictionary topicToSubscribeTo() {
		return MsasMqttTopicsDictionary.MSAS_IO_NOTIFICATIONS_TOPIC;
	}

	@Override
	protected MsasMqttTopicsDictionary onSalleNotFoundTopic() {
		return MsasMqttTopicsDictionary.MSAS_IO_NOTIFICATIONS_ERROR_TOPIC;
	}

	@Override
	protected MsasMqttTopicsDictionary onPersonnelNotFoundTopic() {
		return MsasMqttTopicsDictionary.MSAS_IO_NOTIFICATIONS_ERROR_TOPIC;
	}

	@Override
	protected void onSalleAndPersonnelFound(Salle salle, Personnel personnel,
			String materialId, String[] wholeParams)
			throws MqttPersistenceException, MqttException {

		boolean isRightParam = wholeParams[2].equals("0")
				|| wholeParams[2].equals("1");

		if (isRightParam) {
			this.publish(
					MsasMqttTopicsDictionary.MSAS_IO_NOTIFICATIONS_SUCCESS_TOPIC,
					MsasMqttResponsesDictionary.MSAS_NOTIFICATION_ACKNOWLEDGED,
					materialId);

			boolean isInput = wholeParams[2].equals("0");

			AccesReel accesReel = new AccesReel();
			accesReel.setDate(LocalDate.now());
			accesReel.setHeure(LocalTime.now());
			accesReel.setPersonnel(personnel);
			accesReel.setSalle(salle);
			accesReel.setTypeAcces(isInput ? TypeAcces.ENTREE
					: TypeAcces.SORTIE);

			this.accesReelRepository.save(accesReel);
		} else {
			this.publish(
					MsasMqttTopicsDictionary.MSAS_IO_NOTIFICATIONS_ERROR_TOPIC,
					MsasMqttResponsesDictionary.MSAS_INVALID_PARAM, materialId);
		}
	}
}
