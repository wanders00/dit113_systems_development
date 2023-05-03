
// Libraries
#include <DHT.h>
#include <rpcWiFi.h>
#include <PubSubClient.h>

// Local header files
#include "MqttClient.hpp"
#include "Screen.hpp"
#include "WifiDetails.h"
#include "Settings.hpp"

int maxPeople;

/**
 * Get the value for the maximum number of people allowed in the room.
 * 
 * @return int, The maximum number of people allowed in the room.
 */
int getMaxPeople() {
    return maxPeople;
}

/**
 * Set the value for the maximum number of people allowed in the room.
 * 
 * @return void
 */
void setMaxPeople(int newMaxPeople) {
    Serial.begin(115200);
    maxPeople = newMaxPeople;
    Serial.print(maxPeople);
}