#ifndef SETTINGS_HPP
#define SETTINGS_HPP

// Libraries
#include <DHT.h>
#include <rpcWiFi.h>
#include <PubSubClient.h>

// Local header files
#include "MqttClient.hpp"
#include "Screen.hpp"
#include "WifiDetails.h"
#include "Settings.hpp"

int getMaxPeople();

void setMaxPeople(int newMaxPeople);

#endif