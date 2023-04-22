#ifndef MQTTCLIENT_HPP
#define MQTTCLIENT_HPP

#include <DHT.h>
#include <rpcWiFi.h>
#include "TFT_eSPI.h"
#include <PubSubClient.h>
#include "WifiDetails.h"

void setupWifi();

void callback(char *topic, byte *payload, unsigned int length);

void reconnect();

void mqttInit();

void publishMessage(const char* topic, const char* message);

void publishMessage(const char topic, std::string message);

boolean mqttLoop();

#endif