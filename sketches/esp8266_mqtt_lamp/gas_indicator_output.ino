#include <ESP8266WiFi.h>

#include <PubSubClient.h>

#include <string.h>

const char * ssid = "Home 3";
const char * password = "0632719102";
const char * mqttServer = "192.168.0.102";
const int mqttPort = 1883;
const char * mqttUser = "guest";
const char * mqttPassword = "guest";
const String enable = "Enable";
const String disable = "Disable";
const String data = "{userDeviceId: 2, value: '10'}";
WiFiClient espClient;
PubSubClient client(espClient);


void setup() {

  Serial.begin(9600);

  WiFi.begin(ssid, password);
  delay(1000);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Connecting to WiFi..");
  }
  Serial.println("Connected to the WiFi network");

  client.setServer(mqttServer, mqttPort);

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


}

void loop() {
    const byte numChars = 32;
    char receivedChars[numChars]; 
    boolean newData = false;
  if(Serial.available()) {
    recvWithEndMarker(receivedChars,numChars,newData);
    const String a =  String(receivedChars);
    if(a!= NULL && a.length() > 0) {
      String c = "{\"userDeviceId\":2,\"value\":\"" + a + "\"}"; 
      client.publish("indicator", c.c_str());
      delay(5000);
    }
  }
}

void recvWithEndMarker(char receivedChars[], int numChars , boolean newData ) {
    // an array to store the received data
    static byte ndx = 0;
    char endMarker = '\n';
    char rc;
   
    while (Serial.available() > 0 && newData == false) {
        rc = Serial.read();

        if (rc != endMarker) {
            receivedChars[ndx] = rc;
            ndx++;
            if (ndx >= numChars) {
                ndx = numChars - 1;
            }
        }
        else {
            receivedChars[ndx] = '\0'; // terminate the string
            ndx = 0;
            newData = true;
        }
    }
}
