#ifndef MQTTCLIENT_HPP
#define MQTTCLIENT_HPP

// Libraries
#include <DHT.h>
#include <PubSubClient.h>
#include <rpcWiFi.h>

// Local header files
#include "Screen.hpp"
#include "Util.hpp"
#include "WifiDetails.h"

void mqttInit();

void setupWifi();

void setupMqtt();

boolean mqttLoop();

void playConnectionLostAlert();

void callback(char* topic, byte* payload, unsigned int length);

void reconnect();

void publishMessage(const char* topic, const char* message);

void publishMessage(const char* topic, String message);

bool getWifiConnection();

bool getMqttConnection();

#endif