package com.msas.MSAS.DroitRecognition;

import java.time.LocalDate;
import java.time.LocalTime;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msas.MSAS.DomainModel.Access.TentativeAcces;
import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Personnel.Personnel;
import com.msas.MSAS.Repositories.DroitRepository;
import com.msas.MSAS.Repositories.TentativeAccesRepository;
import com.msas.MSAS.SallePersonnelMqttHandlers.SallePersonnelHandler;
import com.msas.MSAS.mqtt.MsasMqttResponsesDictionary;
import com.msas.MSAS.mqtt.MsasMqttTopicsDictionary;

@Service
public class DroitRecognition extends SallePersonnelHandler {

	@Autowired
	private DroitRepository droitRepository;

	@Autowired
	private TentativeAccesRepository tentativeAccesRepository;

	public DroitRecognition() throws MqttException {
		super();
	}

	@Override
	protected int payloadAsArraySupposedSize() {
		return 2;
	}

	@Override
	protected MsasMqttTopicsDictionary onSalleNotFoundTopic() {
		return MsasMqttTopicsDictionary.MSAS_DROIT_ERROR_TOPIC;
	}

	@Override
	protected MsasMqttTopicsDictionary onPersonnelNotFoundTopic() {
		return MsasMqttTopicsDictionary.MSAS_DROIT_ERROR_TOPIC;
	}

	@Override
	protected void onSalleAndPersonnelFound(Salle salle, Personnel personnel,
			String materialId, String[] wholeParams)
			throws MqttPersistenceException, MqttException {
		boolean response = this.droitRepository.countBySalleAndPersonnel(salle,
				personnel) != 0;

		if (response)
			this.publish(MsasMqttTopicsDictionary.MSAS_DROIT_SUCCESS_TOPIC,
					MsasMqttResponsesDictionary.MSAS_DROIT_RECOGNITION_YES,
					materialId);
		else
			this.publish(MsasMqttTopicsDictionary.MSAS_DROIT_SUCCESS_TOPIC,
					MsasMqttResponsesDictionary.MSAS_DROIT_RECOGNITION_NO,
					materialId);

		TentativeAcces tentativeAcces = new TentativeAcces();
		tentativeAcces.setAutorised(response);
		tentativeAcces.setDate(LocalDate.now());
		tentativeAcces.setHeure(LocalTime.now());
		tentativeAcces.setPersonnel(personnel);
		tentativeAcces.setSalle(salle);

		this.tentativeAccesRepository.save(tentativeAcces);
	}

	@Override
	protected MsasMqttTopicsDictionary topicToSubscribeTo() {
		return MsasMqttTopicsDictionary.MSAS_DROIT_RECOGNITION_TOPIC;
	}
}
