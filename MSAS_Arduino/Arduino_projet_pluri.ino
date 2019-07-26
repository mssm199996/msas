// ------------------------------------------ Libraries section ------------------------------------------

#include <SPI.h>
#include <MFRC522.h>
#include <EEPROM.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

// ------------------------------------------ Static defines section ------------------------------------------

#define NETWORK_SSID_SADR 0
#define NETWORK_PASSWORD_SADR 30
#define MSAS_HARDWARE_ID_SADR 60
#define MOSQUITTO_URL_SADR 90

#define SET_NETWORK_SSID_REQUEST "setNetworkSsid"
#define SET_NETWORK_PASSWORD_REQUEST "setNetworkPassword"
#define SET_MSAS_HARDWARE_ID_REQUEST "setMsasHardwareId"
#define SET_MOSQUITTO_URL "setMosquittoUrl"

#define GET_MSAS_HARDWARE_ID_REQUEST "getMsasHardwareId"
#define GET_MSAS_NETWORK_SSID_REQUEST "getNetworkSsid"
#define GET_MSAS_NETWORK_PASSWORD_REQUEST "getNetworkPassword"
#define GET_MOSQUITTO_URL "getMosquittoUrl"
#define ALARM_TIME 10

String MSAS_GENERAL_PURPOSES_ERROR_TOPIC = "general_purposes/error";
String MSAS_MATERIEL_DISCOVERY_TOPIC = "hardware/discovery/webservice";
String MSAS_MATERIEL_ERROR_TOPIC = "hardware/error";
String MSAS_MATERIEL_WARNING_TOPIC = "hardware/warning";
String MSAS_MATERIEL_SUCCESS_TOPIC = "hardware/success";
String MSAS_DROIT_RECOGNITION_TOPIC = "droit/recognition/webservice";
String MSAS_DROIT_ERROR_TOPIC = "droit/error";
String MSAS_DROIT_SUCCESS_TOPIC = "droit/success";
String MSAS_IO_NOTIFICATIONS_TOPIC = "notifications/io/webservice";
String MSAS_IO_NOTIFICATIONS_ERROR_TOPIC = "notifications/io/error";
String MSAS_IO_NOTIFICATIONS_SUCCESS_TOPIC = "notifications/io/success";
String MSAS_INTRUSION_NOTIFICATIONS_TOPIC = "notifications/intrusion/webservice";
String MSAS_INTRUSION_NOTIFICATIONS_ERROR_TOPIC = "notifications/intrusion/error";
String MSAS_INTRUSION_NOTIFICATIONS_SUCCESS_TOPIC = "notifications/intrusion/success";

#define ACCESS_WAITING_PERIOD 10000
#define KEEP_ALIVE_WAITING_PERIOD 2000
#define NOTIFICATION_INPUT_ATTEMPT_PERIOD 4000
#define INTRUSION_WAITING_PERIOD 10000;

// ------------------------------------------ Dynamic defines section ------------------------------------------

#define connection_state_led_red_controller D0
#define connection_state_led_green_controller D3 //TX Pour WEMOS D1 R2
#define connection_state_led_blue_controller D5 //RX Pour WEMOS D1 R2

#define red_door_led_controller -1
#define green_door_led_controller -1

#define pir_controller D2
#define buzzer_controller D1
#define relay_controller D4

#define administration_pin A0
#define administration_pin_value 0

#define SS_PIN D8
#define RST_PIN D3

#define AUTO_SERIAL_TRIGGERING false
#define COMMIT_EEPROM_WRITE true
#define BEGIN_EEPROM_WR true

// ------------------------------------------ Variables section ------------------------------------------

String NETWORK_SSID = "";
String NETWORK_PASSWORD = "";

String MSAS_HARDWARE_ID = "";

String MOSQUITTO_URL = "";

String DOOR_TYPE = "0";

unsigned long last_keep_alive_instant = 0;
unsigned long last_granted_access_instant = 0;
unsigned long last_notified_input_attempt = 0;
unsigned long last_intrusion_notification = 0;

WiFiClient espClient;
PubSubClient client(espClient);
MFRC522 rfid_receiver(SS_PIN, RST_PIN);

boolean hasAccess = false;
boolean answerArrived = true;
boolean isAffectedToSalle = false;
boolean administrationState = false;
boolean hasJustCrossedThePirAfterBeingAccepted = false;
boolean isConnectedToMosquitto = false;
boolean isConnectedToAccessPoint = false;
boolean ennoughTimeHasEllipsedSinceLastAttempt = true;

String serial_input = "";
String last_read_rfid = "";
String last_arrived_answer = "";

// ------------------------------------------ Main Program section ------------------------------------------

void setup() {
  initAdministrationMode();
  initEEPROMConfiguration();
  
  if (administrationState) {
    initSerialConfiguration();
  } else {
    onInitCallback();
  }

  initNetworkConfiguration();
  initIdentificationConfiguration();

  if(!administrationState)
    EEPROM.end();
}

void loop() {
  if (!administrationState) { 
    if (!isConnectedToAccessPoint) {
      msasWifiBehave();
    } else if (!isConnectedToMosquitto) {
      msasMosquittoBehave();
    } else {
      boolean sendKeepAlive = millis() - last_keep_alive_instant >= KEEP_ALIVE_WAITING_PERIOD;

      if (sendKeepAlive) {
        keepAliveWithMosquitto();

        last_keep_alive_instant = millis();
      }

      if (isAffectedToSalle) {
        msasMotionBehave();
      }
    }
  } else {
    // Only for none-auto-serial-event-micro-controllers
    if (!AUTO_SERIAL_TRIGGERING)
      serialEvent();
  }
}

// ------------------------------------------ My Callbacks section ------------------------------------------

void onInitCallback() {
  pinMode(connection_state_led_red_controller, OUTPUT);
  pinMode(connection_state_led_green_controller, OUTPUT);
  pinMode(connection_state_led_blue_controller, OUTPUT);

  //pinMode(red_door_led_controller, OUTPUT);
  //pinMode(green_door_led_controller, OUTPUT);

  pinMode(pir_controller, INPUT);
  pinMode(buzzer_controller, OUTPUT);
  pinMode(relay_controller, OUTPUT);

  digitalWrite(buzzer_controller, LOW);
  digitalWrite(relay_controller, HIGH);

  SPI.begin();
  rfid_receiver.PCD_Init();
}

void onMosquittoConnectedCallback() {
  changeConnectionLedState(0, 255, 255);

  String fullyMaterielErrorTopic = MSAS_MATERIEL_ERROR_TOPIC + "/" + MSAS_HARDWARE_ID;
  String fullyMaterielWarningTopic = MSAS_MATERIEL_WARNING_TOPIC + "/" + MSAS_HARDWARE_ID;
  String fullyMaterielSuccessTopic = MSAS_MATERIEL_SUCCESS_TOPIC + "/" + MSAS_HARDWARE_ID;

  String fullyDroitRecognitionErrorTopic = MSAS_DROIT_ERROR_TOPIC + "/" + MSAS_HARDWARE_ID;
  String fullyDroitRecognitionSuccessTopic = MSAS_DROIT_SUCCESS_TOPIC + "/" + MSAS_HARDWARE_ID;

  String fullyIoNotificationsErrorTopic = MSAS_IO_NOTIFICATIONS_ERROR_TOPIC + "/" + MSAS_HARDWARE_ID;
  String fullyIoNotificationsSuccessTopic = MSAS_IO_NOTIFICATIONS_SUCCESS_TOPIC + "/" + MSAS_HARDWARE_ID;

  String fullyIntrusionNotificationsErrorTopic = MSAS_INTRUSION_NOTIFICATIONS_ERROR_TOPIC + "/" + MSAS_HARDWARE_ID;
  String fullyIntrusionNotificationsSuccessTopic = MSAS_INTRUSION_NOTIFICATIONS_SUCCESS_TOPIC + "/" + MSAS_HARDWARE_ID;

  char * materielErrorTopic = (char *) (fullyMaterielErrorTopic.c_str());
  char * materielWarningTopic = (char *) (fullyMaterielWarningTopic.c_str());
  char * materielSuccessTopic = (char *) (fullyMaterielSuccessTopic.c_str());

  char * droitRecognitionErrorTopic = (char *) (fullyDroitRecognitionErrorTopic.c_str());
  char * droitRecognitionSuccessTopic = (char *) (fullyDroitRecognitionSuccessTopic.c_str());

  char * ioNotificationsErrorTopic = (char *) (fullyIoNotificationsErrorTopic.c_str());
  char * ioNotificationsSuccessTopic = (char *) (fullyIoNotificationsSuccessTopic.c_str());

  char * intrusionNotificationsErrorTopic = (char *) (fullyIntrusionNotificationsErrorTopic.c_str());
  char * intrusionNotificationsSuccessTopic = (char *) (fullyIntrusionNotificationsSuccessTopic.c_str());

  client.subscribe(materielErrorTopic);
  client.subscribe(materielWarningTopic);
  client.subscribe(materielSuccessTopic);

  client.subscribe(droitRecognitionErrorTopic);
  client.subscribe(droitRecognitionSuccessTopic);

  client.subscribe(ioNotificationsErrorTopic);
  client.subscribe(ioNotificationsSuccessTopic);

  client.subscribe(intrusionNotificationsErrorTopic);
  client.subscribe(intrusionNotificationsSuccessTopic);

  last_read_rfid = "";
  hasAccess = false;

  changeGreenDoorLedState(LOW);
  changeRedDoorLedState(HIGH);
}

// ------------------------------------------ Not my callbacks section ------------------------------------------

void mosquittoCallback(char* topic, byte* payload, unsigned int payload_length) {
  digitalWrite(D8, HIGH);

  last_arrived_answer = "";

  for (int i = 0; i < payload_length; i++) {
    last_arrived_answer.concat((char) payload[i]);
  }

  //Serial.println("Last arrived answer: " + last_arrived_answer);

  answerArrived = true;
}

// ------------------------------------------ Configuration section ------------------------------------------

void initAdministrationMode() {
  pinMode(administration_pin, INPUT);

  byte state = analogRead(administration_pin);

  administrationState = state == administration_pin_value;
}

void initNetworkConfiguration() {
  NETWORK_SSID = readStringFromEEPROM(NETWORK_SSID_SADR);
  NETWORK_PASSWORD = readStringFromEEPROM(NETWORK_PASSWORD_SADR);
  MOSQUITTO_URL = readStringFromEEPROM(MOSQUITTO_URL_SADR);

  if (administrationState) {
    Serial.println("Network initialization finished !");

    Serial.print("Current Network SSID: ");
    Serial.println(NETWORK_SSID);

    Serial.print("Current Network password: ");
    Serial.println(NETWORK_PASSWORD);

    Serial.print("Current Mosquitto url: ");
    Serial.println(MOSQUITTO_URL);
    Serial.flush();
  }
}

void initIdentificationConfiguration() {
  MSAS_HARDWARE_ID = readStringFromEEPROM(MSAS_HARDWARE_ID_SADR);

  if (administrationState) {
    Serial.println("Hardware initialization finished !");

    Serial.print("Current Hardware ID: ");
    Serial.println(MSAS_HARDWARE_ID);
    Serial.flush();
  }
}

void initEEPROMConfiguration() {
  if (BEGIN_EEPROM_WR) {
    EEPROM.begin(1024);
  }
}

void initSerialConfiguration() {
  Serial.begin(9600);

  Serial.println("Entering administration mode");
}

void serialEvent() {
  while (Serial.available() > 0) {
    byte input = Serial.read();

    serial_input += (char) input;

    if (input == 13) {
      // free serial buffer
      while (Serial.available() > 0) {
        char c = Serial.read();
      }

      interpreetRequest(serial_input);

      serial_input = "";
    }
  }
}

void interpreetRequest(String value) {
  String request = value;
  request.replace("\r\n", "");
  request.replace("\n", "");
  request.replace("\r", "");

  if (request.startsWith(SET_NETWORK_SSID_REQUEST)) {
    String ssid = fetchParamsFromFunction(request);

    writeStringToEEPROM(NETWORK_SSID_SADR, ssid);

    Serial.print("Network SSID updated to: ");
    Serial.println(ssid);
    Serial.println("Please restard the micro-controller");
  } else if (request.startsWith(SET_NETWORK_PASSWORD_REQUEST)) {
    String password = fetchParamsFromFunction(request);

    writeStringToEEPROM(NETWORK_PASSWORD_SADR, password);

    Serial.print("Network password updated to: ");
    Serial.println(password);
    Serial.println("Please restard the micro-controller");
  } else if (request.startsWith(SET_MSAS_HARDWARE_ID_REQUEST)) {
    String msasHardwareId = fetchParamsFromFunction(request);

    writeStringToEEPROM(MSAS_HARDWARE_ID_SADR, msasHardwareId);

    Serial.print("MSAS Hardware ID update to: ");
    Serial.println(msasHardwareId);
    Serial.println("Please restard the micro-controller");
  } else if (request.startsWith(SET_MOSQUITTO_URL)) {
    String mosquittoUrl = fetchParamsFromFunction(request);

    writeStringToEEPROM(MOSQUITTO_URL_SADR, mosquittoUrl);

    Serial.print("Mosquitto URL updated to: ");
    Serial.println(mosquittoUrl);
    Serial.println("Please restard the micro-controller");
  } else if (request.startsWith(GET_MSAS_HARDWARE_ID_REQUEST)) {
    Serial.print("MSAS Hardware ID: ");
    Serial.println(MSAS_HARDWARE_ID);
  } else if (request.startsWith(GET_MSAS_NETWORK_SSID_REQUEST)) {
    Serial.print("Network SSID: ");
    Serial.println(NETWORK_SSID);
  } else if (request.startsWith(GET_MSAS_NETWORK_PASSWORD_REQUEST)) {
    Serial.print("Network Password: ");
    Serial.println(NETWORK_PASSWORD);
  } else if (request.startsWith(GET_MOSQUITTO_URL)) {
    Serial.print("Mosquitto URL: ");
    Serial.println(MOSQUITTO_URL);
  } else {
    Serial.print("Unsupported request: ");
    Serial.println(request);
  }

  Serial.flush();
}

// ------------------------------------------ Wifi section ------------------------------------------

boolean connectToAccessPoint() {
  WiFi.softAP(NETWORK_SSID, NETWORK_PASSWORD);

  return WiFi.status() == WL_CONNECTED;
}

boolean hasLostConnectionToAccessPoint() {
  return WiFi.status() != WL_CONNECTED;
}

void msasWifiBehave() {
  changeConnectionLedState(255, 0, 0);

  isConnectedToAccessPoint = connectToAccessPoint();
}

// ------------------------------------------ RFID section ------------------------------------------

String readRfidTag() {
  if (rfid_receiver.PICC_IsNewCardPresent()
      && rfid_receiver.PICC_ReadCardSerial()) {

    String content;

    for (byte i = 0; i < rfid_receiver.uid.size; i++) {
      content.concat(String(rfid_receiver.uid.uidByte[i], HEX));
    }

    content.toUpperCase();

    return content;
  } else return "";
}

// ------------------------------------------ Mosquitto section ------------------------------------------

boolean connectToMosquitto() {
  isConnectedToAccessPoint = !hasLostConnectionToAccessPoint();

  if (!isConnectedToAccessPoint) {
    return false;
  } else {
    String dns = getDNSFromNgrokUrl(MOSQUITTO_URL);
    String port = getPortFromNgrokUrl(MOSQUITTO_URL);

    client.setServer(dns.c_str(), port.toInt());
    client.setCallback(mosquittoCallback);

    char *hardwareId = (char*)MSAS_HARDWARE_ID.c_str();

    return client.connect(hardwareId);
  }
}

String sendRequestToMosquitto(String topic, String request) {
  char* topicAsPointer = (char* )topic.c_str();
  char* requestAsPointer = (char* )request.c_str();

  //Serial.println("Trying to publish to topic: " + topic);
  //printCharArrayToConsole(topicAsPointer);
  //Serial.println("The following information: " + request);
  //printCharArrayToConsole(requestAsPointer);

  client.publish(topicAsPointer, requestAsPointer, false);

  answerArrived = false;

  while (isConnectedToAccessPoint && isConnectedToMosquitto && !answerArrived) {
    isConnectedToAccessPoint = !hasLostConnectionToAccessPoint();

    if (isConnectedToAccessPoint) {
      isConnectedToMosquitto = client.connected();

      if (isConnectedToMosquitto) {
        client.loop();
      }
    }
  }

  return last_arrived_answer;
}

void keepAliveWithMosquitto() {
  isAffectedToSalle = false;

  String response = sendRequestToMosquitto(MSAS_MATERIEL_DISCOVERY_TOPIC, MSAS_HARDWARE_ID);

  boolean isThereResponse = !response.equals("");

  if (isThereResponse) {
    if (response.startsWith("07")) {
      changeConnectionLedState(0, 0, 255);

      delay(1000);
    } else if (response.startsWith("03")) {
      changeConnectionLedState(0, 255, 0);

      isAffectedToSalle = true;
    }
  } else {
    isConnectedToMosquitto = false;
    isConnectedToAccessPoint = false;
  }
}

void msasMosquittoBehave() {
  changeConnectionLedState(255, 0, 255);

  isConnectedToMosquitto = connectToMosquitto();

  if (isConnectedToMosquitto) {
    onMosquittoConnectedCallback();
  }
}

// ------------------------------------------ Busness Logic section ------------------------------------------

boolean checkIfHasAccessRight(String rfid) {
  // mark as orange rfid led
  // envoyer <hardwareId>;<rfid> au topic "droit/recognition/webservice"

  String response = sendRequestToMosquitto(MSAS_DROIT_RECOGNITION_TOPIC,
                    MSAS_HARDWARE_ID + ";" + rfid);

  if (!response.equals("")) {
    if (response.startsWith("10") || response.equals("10")) {
      return false;
    } else if (response.startsWith("09") || response.equals("09")) {
      return true;
    }
  } else {
    isConnectedToAccessPoint = false;
    isConnectedToMosquitto = false;
  }

  return false;
}

void notifyInput(String rfid) {
  boolean allowNotify = millis() - last_notified_input_attempt >= NOTIFICATION_INPUT_ATTEMPT_PERIOD;

  if (allowNotify) {
    sendRequestToMosquitto(MSAS_IO_NOTIFICATIONS_TOPIC,
                           MSAS_HARDWARE_ID + ";" + rfid + ";" + DOOR_TYPE);

    last_notified_input_attempt = millis();
  }
}

String notifyIntrusion() {
  unsigned long dt = millis() - last_intrusion_notification;

  boolean ennoughTimeHasEllipsedSinceLastIntrusion = dt >= INTRUSION_WAITING_PERIOD;

  if(ennoughTimeHasEllipsedSinceLastIntrusion) {
    last_intrusion_notification = millis();
    
    return sendRequestToMosquitto(MSAS_INTRUSION_NOTIFICATIONS_TOPIC, MSAS_HARDWARE_ID);
  }
  else return "11"; // HIgh five
}

// ------------------------------------------ Busness Logic section ------------------------------------------

void markInstant() {
  unsigned long dt = millis() - last_granted_access_instant;

  ennoughTimeHasEllipsedSinceLastAttempt = dt >= ACCESS_WAITING_PERIOD;

  if (ennoughTimeHasEllipsedSinceLastAttempt) {
    hasAccess = false;

    closeDoor();
    last_read_rfid = "";
    changeRedDoorLedState(HIGH);
    changeGreenDoorLedState(LOW);
  }
}

void msasMotionBehave() {
  // Mark as RED, RFID state LED (optional)

  boolean isUnderMotion = getPirState();

  //Serial.print("isUnderMotion (PIR value): ");
  //Serial.println(isUnderMotion);

  //Serial.print("hasAccess: ");
  //Serial.println(hasAccess);

  // Pour éviter de détecter une intrusion alors que la personne éssaye de rentrer (elle a l'accès mais hasAccess a été altéré lar markInstant())
  if (!isUnderMotion) {
    hasJustCrossedThePirAfterBeingAccepted = false;
  }

  if (isUnderMotion && !hasAccess && !hasJustCrossedThePirAfterBeingAccepted) {
    turnOnBuzzer();

    String response = notifyIntrusion();

    if (response.startsWith("11")) {
      for (int i = 0; i < ALARM_TIME; i++) {
        turnOnBuzzer();
        delay(500);
        turnOffBuzzer();
        delay(500);
      }
    } else {
      isConnectedToMosquitto = false;
      isConnectedToAccessPoint = false;
    }

    turnOffBuzzer();
  } else if (!isUnderMotion) {
    String tag = readRfidTag();

    if (!tag.equals("") && ennoughTimeHasEllipsedSinceLastAttempt) {
      // mark as orange rfid led (optional)
      last_read_rfid = tag;

      doubleBeep();

      hasAccess = checkIfHasAccessRight(tag);
      // mark as green rfid led (optional)

      if (hasAccess) {
        changeRedDoorLedState(LOW);
        changeGreenDoorLedState(HIGH);

        openDoor();

        last_granted_access_instant = millis();
      }
    }
  } else if (isUnderMotion && hasAccess) {
    notifyInput(last_read_rfid);

    last_granted_access_instant = 0;

    hasJustCrossedThePirAfterBeingAccepted = true;
  }

  markInstant();
}

// ------------------------------------------ Utils section ------------------------------------------
void doubleBeep() {
  turnOnBuzzer();
  delay(200);
  turnOffBuzzer();
  delay(200);
  turnOnBuzzer();
  delay(200);
  turnOffBuzzer();
}

void openDoor() {
  changeRelayState(LOW);
}

void closeDoor() {
  changeRelayState(HIGH);
}

void changeRelayState(uint8_t state) {
  digitalWrite(relay_controller, state);
}

void turnOnBuzzer() {
  digitalWrite(buzzer_controller, HIGH);
}

void turnOffBuzzer() {
  digitalWrite(buzzer_controller, LOW);
}

boolean getPirState() {
  return digitalRead(pir_controller) == HIGH;
}

void changeGreenDoorLedState(uint8_t state) {
  //digitalWrite(green_door_led_controller, state);
}

void changeRedDoorLedState(uint8_t state) {
  //digitalWrite(red_door_led_controller, state);
}

void changeConnectionLedState(uint8_t red, uint8_t green, uint8_t blue) {
  digitalWrite(connection_state_led_red_controller, red);
  digitalWrite(connection_state_led_green_controller, green);
  digitalWrite(connection_state_led_blue_controller, blue);
}

String fetchParamsFromFunction(String function) {
  return function.substring(function.indexOf('(') + 1,
                            function.lastIndexOf(')'));
}

void writeByteToEEPROM(int add, byte data) {
  EEPROM.write(add, data);

  if (COMMIT_EEPROM_WRITE) {
    EEPROM.commit();
  }
}

byte readByteFromEEPROM(int add) {
  return EEPROM.read(add);
}

void writeStringToEEPROM(int add, String data) {
  int dataSize = data.length();

  Serial.print("Trying to write: ");
  Serial.print(dataSize);
  Serial.println(" bytes");

  for (int i = 0; i < dataSize; i++) {
    EEPROM.write(add + i, data[i]);
  }

  EEPROM.write(add + dataSize, '\0'); // To detect wether the string is fully-read

  if (COMMIT_EEPROM_WRITE) {
    Serial.println("Commiting");

    EEPROM.commit();
  }
}

String readStringFromEEPROM(char add) {
  char data[127];
  int offset = 0;

  unsigned char k = EEPROM.read(add);

  while (k != '\0' && offset < 126) {   //Read until null character
    k = EEPROM.read(add + offset);

    data[offset] = k;

    offset++;
  }

  data[offset] = '\0';

  return String(data);
}

void printCharArrayToConsole(const char value[]) {
  for (int i = 0; i < 256; i++) {
    char c = value[i];

    if (c != '\0')
      Serial.print((char) c);
    else break;
  }

  Serial.println();
}

IPAddress fromStringToIPAdress(String ipAdress) {
  int result[4] = {0, 0, 0, 0};
  int part = 0;

  for (int i = 0; i < ipAdress.length(); i++ ) {
    char c = ipAdress[i];

    if ( c == '.' ) {
      part++;

      continue;
    }

    result[part] *= 10;
    result[part] += c - '0';
  }

  return IPAddress(result[0], result[1], result[2], result[3]);
}

String getPortFromNgrokUrl(String url) {
  return url.substring(url.lastIndexOf(':') + 1);
}

String getDNSFromNgrokUrl(String url){
  return url.substring(0, url.lastIndexOf(':'));
}
