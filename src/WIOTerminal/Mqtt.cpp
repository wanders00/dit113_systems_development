#include <rpcWiFi.h>
#include <PubSubClient.h>
#include "Mqtt.hpp" 

// MQTT parameters for the terminal based on workshop example
// and https://www.tutorialspoint.com/esp32_for_iot/esp32_for_iot_transmitting_data_over_wifi_using_mqtt.htm

// settings for using Wifi
const char* ssid = "William's Galaxy S10e";
const char* password  = "vwvg6416";

// temporary using web service for MQTT broker
const char* server = "broker.hivemq.com"; 
int mqttPort = 1883;

const char* mqttClientName= "ESPYS2111";

// topics
const char* TOPIC_Locusimperium = "wio/locusimperium";
const char* TOPIC_Ultrasonic = "wio/locusimperium/ultrasonic";
const char* TOPIC_Temperature = "wio/locusimperium/temperature";

WiFiClient wioClient;
PubSubClient client(wioClient);

void callback(char* topic, byte* payload, unsigned int length) {
   Serial.print("Message received from: "); Serial.println(topic);
   for (int i = 0; i < length; i++) {
      Serial.print((char)payload[i]);
   }
   Serial.println();
   Serial.println();
}


void connectionSetup(){
    Serial.begin(115200);
    WiFi.mode(WIFI_STA);                    //The WiFi is in station mode 
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print(".");
   }
    Serial.println("");  Serial.print("WiFi connected to: "); Serial.println(ssid);  Serial.println("IP address: ");     Serial.println(WiFi.localIP());
    delay(2000);
    client.setServer(server, mqttPort);
    client.setCallback(callback);
}

void checkConnection(){
    if (!client.connected()){
      while (!client.connect(mqttClientName)){
          Serial.print("Attempting MQTT connection...");
          client.subscribe(TOPIC_Locusimperium); // subscribed to this one just to test
        }
    }else{
        Serial.print("MQTT connected");
    }   
}