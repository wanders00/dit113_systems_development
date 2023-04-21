#ifndef MQTT_HPP
#define MQTT_HPP

#include <rpcWiFi.h>
#include <PubSubClient.h>

extern const char* ssid;
extern const char* password;

extern const char* server;
extern int mqttPort;

extern const char* mqttClientName;
extern const char* TOPIC_Locusimperium;
extern const char* TOPIC_Ultrasonic;
extern const char* TOPIC_Temperature;

extern WiFiClient wioClient;
extern PubSubClient client;

void callback(char* topic, byte* payload, unsigned int length);

void connectionSetup();

void checkConnection();

#endif