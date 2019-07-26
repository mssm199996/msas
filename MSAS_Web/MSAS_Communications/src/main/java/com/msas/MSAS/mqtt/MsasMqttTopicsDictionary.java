package com.msas.MSAS.mqtt;

import com.msas.MSAS.DomainModel.Authentification.Materiel;

public enum MsasMqttTopicsDictionary {

	MSAS_GENERAL_PURPOSES_ERROR_TOPIC("general_purposes/error"),
	
	// Data: [0] => ID Materiel
	MSAS_MATERIEL_DISCOVERY_TOPIC("hardware/discovery/webservice"),
	MSAS_MATERIEL_ERROR_TOPIC("hardware/error"),
	MSAS_MATERIEL_WARNING_TOPIC("hardware/warning"),
	MSAS_MATERIEL_SUCCESS_TOPIC("hardware/success"),
	
	// Data: [0] => ID Materiel ; [1] => ID Personnel
	MSAS_DROIT_RECOGNITION_TOPIC("droit/recognition/webservice"),
	MSAS_DROIT_ERROR_TOPIC("droit/error"),
	MSAS_DROIT_SUCCESS_TOPIC("droit/success"),
	
	// Data: [0] => ID Materiel ; [1] => ID Personnel ; [2] 0/1 (I/O)
	MSAS_IO_NOTIFICATIONS_TOPIC("notifications/io/webservice"),
	MSAS_IO_NOTIFICATIONS_ERROR_TOPIC("notifications/io/error"),
	MSAS_IO_NOTIFICATIONS_SUCCESS_TOPIC("notifications/io/success"),
		
	// Data: [0] => ID Materiel
	MSAS_INTRUSION_NOTIFICATIONS_TOPIC("notifications/intrusion/webservice"),
	MSAS_INTRUSION_NOTIFICATIONS_ERROR_TOPIC("notifications/intrusion/error"),
	MSAS_INTRUSION_NOTIFICATIONS_SUCCESS_TOPIC("notifications/intrusion/success");

	private String topic;

	MsasMqttTopicsDictionary(String topic) {
		this.topic = topic;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public boolean compareToTopic(String topic) {
		return this.getTopic().equals(topic);
	}

	public String addMaterielSerialNumber(Materiel materiel) {
		return this.addMaterielSerialNumber(materiel.getSerialNumber());
	}

	public String addMaterielSerialNumber(String serialNumber) {
		return this.getTopic() + "/" + serialNumber;
	}

	@Override
	public String toString() {
		return this.topic;
	}
}
