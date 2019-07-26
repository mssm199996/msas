package com.msas.MSAS.SallePersonnelMqttHandlers;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.DomainModel.Personnel.Personnel;
import com.msas.MSAS.Repositories.PersonnelRepository;
import com.msas.MSAS.mqtt.MsasMqttResponsesDictionary;
import com.msas.MSAS.mqtt.MsasMqttTopicsDictionary;

public abstract class SallePersonnelHandler extends SalleHandler {

	@Autowired
	private PersonnelRepository personnelRepository;

	public SallePersonnelHandler() throws MqttException {
		super();
	}

	@Override
	protected void onSalleFound(Salle salle, String materielId,
			String[] wholeParams) throws MqttPersistenceException,
			MqttException {

		String personnelId = wholeParams[1];

		Personnel personnel = this.personnelRepository
				.findBySerialNumber(personnelId);

		if (personnel != null) {
			this.onSalleAndPersonnelFound(salle, personnel, materielId,
					wholeParams);
		} else
			this.publish(this.onPersonnelNotFoundTopic(),
					MsasMqttResponsesDictionary.MSAS_UNKNOWN_PERSONNEL,
					materielId);
	}

	protected abstract MsasMqttTopicsDictionary onPersonnelNotFoundTopic();

	protected abstract void onSalleAndPersonnelFound(Salle salle,
			Personnel personnel, String materialId, String[] wholeParams)
			throws MqttPersistenceException, MqttException;
}
