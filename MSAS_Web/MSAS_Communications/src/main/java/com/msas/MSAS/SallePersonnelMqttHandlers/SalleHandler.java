package com.msas.MSAS.SallePersonnelMqttHandlers;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;

import com.msas.MSAS.DomainModel.Authentification.Salle;
import com.msas.MSAS.Repositories.SalleRepository;
import com.msas.MSAS.mqtt.MQTTHandler;
import com.msas.MSAS.mqtt.MsasMqttResponsesDictionary;
import com.msas.MSAS.mqtt.MsasMqttTopicsDictionary;

public abstract class SalleHandler extends MQTTHandler {

	@Autowired
	private SalleRepository salleRepository;

	public SalleHandler() throws MqttException {
		super();
	}

	@PostConstruct
	public void onAfterConstruct() throws MqttException {
		this.connect();
		this.subscribe(this.topicToSubscribeTo());
	}

	@Override
	public void connectionLost(Throwable cause) {
	}

	@Override
	public void messageArrived(String topic, MqttMessage message)
			throws Exception {

		String payloadAsString = new String(message.getPayload());
		String[] payloadAsArray = payloadAsString.split(";");

		if (payloadAsArray.length == this.payloadAsArraySupposedSize()) {
			this.onPayloadRecognized(payloadAsArray);
		} else if (payloadAsArray.length >= 1) {
			this.publish(
					MsasMqttTopicsDictionary.MSAS_GENERAL_PURPOSES_ERROR_TOPIC,
					MsasMqttResponsesDictionary.MSAS_UNEXPECTED_PARAMS_COUNT_RESPONSE,
					payloadAsArray[0]);
		} else {
			this.publish(
					MsasMqttTopicsDictionary.MSAS_GENERAL_PURPOSES_ERROR_TOPIC,
					MsasMqttResponsesDictionary.MSAS_UNEXPECTED_PARAMS_COUNT_RESPONSE);
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}

	public void onPayloadRecognized(String[] payloadAsArray)
			throws MqttPersistenceException, MqttException {

		String materielId = payloadAsArray[0];

		Salle salle = this.salleRepository
				.findByMaterielSerialNumber(materielId);

		if (salle != null) {
			this.onSalleFound(salle, materielId, payloadAsArray);
		} else {
			this.publish(this.onSalleNotFoundTopic(),
					MsasMqttResponsesDictionary.MSAS_UNAFFECTED_SALLE_TO_MATERIEL,
					materielId);
		}
	}

	protected abstract int payloadAsArraySupposedSize();

	protected abstract MsasMqttTopicsDictionary topicToSubscribeTo();

	protected abstract MsasMqttTopicsDictionary onSalleNotFoundTopic();

	protected abstract void onSalleFound(Salle salle, String materialId,
			String[] wholeParams) throws MqttPersistenceException,
			MqttException;
}