#include <ESP8266WiFi.h>

#include <PubSubClient.h>

#include <string.h>

const char * ssid = "SSID";
const char * password = "PASS";
const char * mqttServer = "192.168.1.140";
const int mqttPort = 1883;
const char * mqttUser = "guest";
const char * mqttPassword = "guest";
const String enable = "Enable";
const String disable = "Disable";
WiFiClient espClient;
PubSubClient client(espClient);

void setup() {

  Serial.begin(115200);

  WiFi.begin(ssid, password);
  delay(1000);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Connecting to WiFi..");
  }
  Serial.println("Connected to the WiFi network");

  client.setServer(mqttServer, mqttPort);
  client.setCallback(callback);

  while (!client.connected()) {
    Serial.println("Connecting to MQTT...");

    if (client.connect("ESP8266Client", mqttUser, mqttPassword)) {

      Serial.println("connected");

    } else {

      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);

    }
  }

  //client.publish("esp/command", "Hello from ESP8266");
  client.subscribe("esp/command/1");
  client.subscribe("esp/command/2");

  pinMode(2, OUTPUT);
  digitalWrite(2, LOW);
}

void callback(char * topic, byte * payload, unsigned int length) {

  Serial.print("Message arrived in topic: ");
  Serial.println(topic);

  Serial.print("Message:");
  for (int i = 0; i < length; i++) {
    Serial.print((char) payload[i]);
  }
  Serial.println();
  String messageString = String((char * ) payload);
  int delimIndex = messageString.indexOf(':');
  String command = getValue(messageString, 0, delimIndex);
  Serial.print("command=");
  Serial.println(command);
  int pin = getValue(messageString, delimIndex + 1, length).toInt();
  if (strcmp(disable.c_str(), command.c_str()) == 0) {
    Serial.println("disable");
    digitalWrite(pin, LOW);
    delay(1000);
  } else if (strcmp(enable.c_str(), command.c_str()) == 0) {
    Serial.println("enable");
    digitalWrite(pin, HIGH);
    delay(1000);
  }
  Serial.println("-----------------------");
}

String getValue(String command, int from, int to) {
  String value = command.substring(from, to);
  return value;
}

void loop() {
  client.loop();
}