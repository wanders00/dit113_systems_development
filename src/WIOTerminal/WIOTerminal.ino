// Arduino libraries
#include <DHT.h>
#include <Ultrasonic.h>

#include "Arduino.h"
#include "TFT_eSPI.h"

// Local header files
#include "Buzzer.hpp"
#include "Loudness.hpp"
#include "MqttClient.hpp"
#include "Screen.hpp"
#include "TempHumidity.hpp"
#include "Ultrasonic.hpp"
#include "Util.hpp"
#include "WifiDetails.h"
#include "Loudness.hpp"
#include "Buzzer.hpp"
#include "Settings.hpp"

// Ultrasonic
int countMain = 0;

// Screen update
const uint32_t screenUpdateFrequency = 1000;
uint32_t lastTimeScreenUpdated;

// MQTT publish frequency values
const uint32_t publishDelayTime = 1000;
uint32_t lastTimePublished;

// MQTT subscription topics
const char *PEOPLE_COUNT_TOPIC = "LocusImperium/WIO/peopleCount";
const char *TEMPERATURE_TOPIC = "LocusImperium/WIO/temperatureValue";
const char *HUMIDITY_TOPIC = "LocusImperium/WIO/humidityValue";
const char *LOUDNESS_TOPIC = "LocusImperium/WIO/loudnessValue";

void setup() {
    screenInit();

    mqttInit();

    tempHumidInit();

    loudnessInit();

    buzzerInit();

    flashScreen();

    lastTimePublished = 0;

    lastTimeScreenUpdated = 0;
}

void loop() {
    setCurrentTime(millis());

    if (getCurrentTime() - lastTimeScreenUpdated > screenUpdateFrequency) {
        updateScreen();
        lastTimeScreenUpdated = getCurrentTime();
    }

    /* UltrasonicData data;
    data = detectMovement(countMain);
    countMain = data.count; */

    if (getCurrentTime() - lastTimePublished > publishDelayTime) {
        if (mqttLoop()) {
            // publishMessage(PEOPLE_COUNT_TOPIC, String(countMain));
            // publishMessage(TEMPERATURE_TOPIC, String(measureTemperature()));
            // publishMessage(HUMIDITY_TOPIC, String(measureHumidity()));
            publishMessage(LOUDNESS_TOPIC, String(loudnessLevel()));
            lastTimePublished = getCurrentTime();
        }
    }
    displayPeopleCount(countMain);
}
