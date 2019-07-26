package com.msas.MSAS.mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.msas.MSAS.DomainModel.Authentification.Materiel;

public abstract class MQTTHandler extends MqttClient implements MqttCallback {

	public static final int QOS = 1;

	private String brokerUri;
	private MqttConnectOptions mqttConnectOptions;

	public MQTTHandler(String brokerUri) throws MqttException {
		super(brokerUri, UUID.randomUUID().toString(), new MemoryPersistence());

		this.setCallback(this);

		this.brokerUri = brokerUri;
		this.mqttConnectOptions = new MqttConnectOptions();
		this.mqttConnectOptions.setAutomaticReconnect(true);
		this.mqttConnectOptions.setConnectionTimeout(10);
		this.mqttConnectOptions.setCleanSession(true);
	}

	public MQTTHandler() throws MqttException {
		this("tcp://localhost:1883");
	}

	public void connect() throws MqttSecurityException, MqttException {
		this.connect(this.mqttConnectOptions);
	}

	@Override
	public void disconnect() throws MqttException {
		super.disconnect();
		super.close();
	}
	
	public void subscribe(MsasMqttTopicsDictionary topic) throws MqttException {
		super.subscribe(topic.getTopic());
	}

	public void publish(String topic, String content)
			throws MqttPersistenceException, MqttException {
		MqttMessage message = new MqttMessage(content.getBytes());
		message.setQos(MQTTHandler.QOS);

		this.publish(topic, message);
	}

	public void publish(MsasMqttTopicsDictionary topic,
			MsasMqttResponsesDictionary response)
			throws MqttPersistenceException, MqttException {
		String topicAsString = topic.getTopic();
		String responseAsString = response.getResponse();

		this.publish(topicAsString, responseAsString);
	}

	public void publish(MsasMqttTopicsDictionary topic,
			MsasMqttResponsesDictionary response, String serialNumber)
			throws MqttPersistenceException, MqttException {
		String topicAsString = topic.addMaterielSerialNumber(serialNumber);
		String responseAsString = response.getResponse();

		this.publish(topicAsString, responseAsString);
	}

	public void publish(MsasMqttTopicsDictionary topic,
			MsasMqttResponsesDictionary response, Materiel materiel)
			throws MqttPersistenceException, MqttException {
		this.publish(topic, response, materiel.getSerialNumber());
	}

	public String getBrokerUri() {
		return brokerUri;
	}

	public void setBrokerUri(String brokerUri) {
		this.brokerUri = brokerUri;
	}

	public String getClientId() {
		return this.aClient.getClientId();
	}

	public MqttConnectOptions getMqttConnectOptions() {
		return mqttConnectOptions;
	}

	public void setMqttConnectOptions(MqttConnectOptions mqttConnectOptions) {
		this.mqttConnectOptions = mqttConnectOptions;
	}

	@Override
	public String toString() {
		return this.getClientId();
	}
}
