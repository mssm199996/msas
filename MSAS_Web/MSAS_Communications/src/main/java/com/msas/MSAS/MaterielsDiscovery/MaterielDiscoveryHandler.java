package com.msas.MSAS.MaterielsDiscovery;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.msas.MSAS.DomainModel.Authentification.Materiel;
import com.msas.MSAS.Repositories.MaterielRepository;
import com.msas.MSAS.mqtt.MQTTHandler;
import com.msas.MSAS.mqtt.MsasMqttResponsesDictionary;
import com.msas.MSAS.mqtt.MsasMqttTopicsDictionary;

@Service
public class MaterielDiscoveryHandler extends MQTTHandler {

	public MaterielDiscoveryHandler() throws MqttException {
		super();
	}

	public static final long RESET_MATERIEL_PERIOD = 5_000;

	@Autowired
	private MaterielRepository materielRepository;

	@PostConstruct
	public void onAfterConstruct() throws MqttException {
		this.connect();
		this.subscribe(MsasMqttTopicsDictionary.MSAS_MATERIEL_DISCOVERY_TOPIC);
	}

	@Scheduled(fixedDelay = MaterielDiscoveryHandler.RESET_MATERIEL_PERIOD)
	public void resetMateriels() {
		LocalDateTime limit = LocalDateTime.now().minusSeconds(
				MaterielDiscoveryHandler.RESET_MATERIEL_PERIOD / 1_000);

		this.materielRepository.updateAllMaterielEtat(false, limit);
	}

	@Override
	public void connectionLost(Throwable cause) {
	}

	@Override
	public void messageArrived(String topic, MqttMessage message)
			throws Exception {

		if (MsasMqttTopicsDictionary.MSAS_MATERIEL_DISCOVERY_TOPIC
				.compareToTopic(topic)) {
			this.onDiscoveryMessageArrived(new String(message.getPayload()));
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}

	private void onDiscoveryMessageArrived(String serialNumber)
			throws MqttPersistenceException, MqttException {
		Materiel materiel = this.materielRepository
				.findBySerialNumberWithMateriel(serialNumber);

		if (materiel == null) {
			materiel = new Materiel();
			materiel.setEtat(true);
			materiel.setSerialNumber(serialNumber);
		} else
			materiel.setEtat(true);

		materiel.setLastUpdate(LocalDateTime.now());

		this.materielRepository.save(materiel);

		if (materiel.getSalle() == null)
			this.publish(
					MsasMqttTopicsDictionary.MSAS_MATERIEL_WARNING_TOPIC,
					MsasMqttResponsesDictionary.MSAS_UNAFFECTED_SALLE_TO_MATERIEL,
					serialNumber);
		else
			this.publish(
					MsasMqttTopicsDictionary.MSAS_MATERIEL_SUCCESS_TOPIC,
					MsasMqttResponsesDictionary.MSAS_DISCOVERY_SUCCESSFUL_RESPONSE,
					serialNumber);
	}
}
