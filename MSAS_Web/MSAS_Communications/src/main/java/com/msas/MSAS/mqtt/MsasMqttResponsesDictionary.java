package com.msas.MSAS.mqtt;

public enum MsasMqttResponsesDictionary {
	MSAS_INVALID_PARAM("01 Un parametre est invalide !"),
	MSAS_UNEXPECTED_PARAMS_COUNT_RESPONSE("02 Nombre de parametres invalide !"),
	
	MSAS_DISCOVERY_SUCCESSFUL_RESPONSE("03 Materiel reconnu"),
	
	MSAS_UNAFFECTED_SALLE_TO_PERSONNEL_SURVEILLANCE("06 Aucun personnel de surveillance n'est affecte a la salle"),
	MSAS_UNAFFECTED_SALLE_TO_MATERIEL("07 Aucune salle n'est affecte au materiel"),
	MSAS_UNKNOWN_PERSONNEL("08 Personnel invalide"),
	
	MSAS_DROIT_RECOGNITION_YES("09 Bienvenu !"),
	MSAS_DROIT_RECOGNITION_NO("10 Acces refuse !"),
	
	MSAS_NOTIFICATION_ACKNOWLEDGED("11 High five !"),
	MSAS_NOTIFICATION_INTRUSION_DETECTED("12 Intrusion !");
	
	private String response;

	MsasMqttResponsesDictionary(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
