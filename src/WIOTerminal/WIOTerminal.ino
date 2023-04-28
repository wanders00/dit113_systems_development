#include "TFT_eSPI.h"
#include <Ultrasonic.h>
#include "Arduino.h"
#include <DHT.h>

// Local header files
#include "Ultrasonic.hpp"
#include "Screen.hpp"
#include "TempHumidity.hpp"
#include "MqttClient.hpp"
#include "WifiDetails.h"
#include "Loudness.hpp"
#include "Buzzer.hpp"

int countMain = 0;
int publishDelayTime = 500;
uint32_t lastTimePublished;

const char *PEOPLE_COUNT_TOPIC = "LocusImperium/WIO/peopleCount";
const char *TEMPERATURE_TOPIC = "LocusImperium/WIO/temperatureValue";
const char *HUMIDITY_TOPIC = "LocusImperium/WIO/humidityValue";
const char *LOUDNESS_TOPIC = "LocusImperium/WIO/loudnessValue";

void setup() {
    screenInit();

    mqttInit();

    tempHumidInit();

    initializeLoudness();

    buzzerInit();

    flashScreen();

    lastTimePublished = millis();
}

void loop() {
    UltrasonicData data = detectMovement(countMain);
    countMain = data.count;
    if (mqttLoop()) {
        uint32_t currentTime = millis();
        if (currentTime - lastTimePublished > publishDelayTime) {
            publishMessage(PEOPLE_COUNT_TOPIC, String(countMain));
            publishMessage(TEMPERATURE_TOPIC, String(measureTemperature()));
            publishMessage(HUMIDITY_TOPIC, String(measureHumidity()));
            publishMessage(LOUDNESS_TOPIC, String(loudnessLevel()));
        }
    }
    displayPeopleCountDebug(data.count, data.distance1, data.distance2);
}