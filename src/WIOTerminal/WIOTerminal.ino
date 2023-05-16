// Arduino libraries
#include <DHT.h>
#include <Ultrasonic.h>

#include "Arduino.h"
<<<<<<< src/WIOTerminal/WIOTerminal.ino
#include "TFT_eSPI.h"
=======
>>>>>>> src/WIOTerminal/WIOTerminal.ino

// Local header files
#include "Buzzer.hpp"
#include "Loudness.hpp"
#include "MqttClient.hpp"
#include "Screen.hpp"
#include "Settings.hpp"
#include "TempHumidity.hpp"
#include "Ultrasonic.hpp"
#include "Util.hpp"
#include "WifiDetails.h"
<<<<<<< src/WIOTerminal/WIOTerminal.ino
#include "Settings.hpp"
=======
>>>>>>> src/WIOTerminal/WIOTerminal.ino

// Ultrasonic
int countMain = 0;

// Screen update
const uint32_t screenUpdateFrequency = 1000;
uint32_t lastTimeScreenUpdated;

// MQTT publish frequency values
<<<<<<< src/WIOTerminal/WIOTerminal.ino
const uint32_t publishDelayTime = 1000;
=======
const uint32_t publishDelayTime = 3000;
>>>>>>> src/WIOTerminal/WIOTerminal.ino
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

    lastTimePublished = 0;

    lastTimeScreenUpdated = 0;

<<<<<<< src/WIOTerminal/WIOTerminal.ino
    lastTimePublished = 0;

    lastTimeScreenUpdated = 0;
=======
    setMaxPeople(10);
    setMaxTemperature(30);
    setMaxHumidity(30);
    setMaxLoudness(50);
>>>>>>> src/WIOTerminal/WIOTerminal.ino
}

void loop() {
    setCurrentTime(millis());

<<<<<<< src/WIOTerminal/WIOTerminal.ino
=======
    buzzerLoop();

    UltrasonicData data;
    data = detectMovement(getPeople());

    setPeople(data.count);
    setTemperature(measureTemperature());
    setHumidity(measureHumidity());
    setLoudness(loudnessMapped());

>>>>>>> src/WIOTerminal/WIOTerminal.ino
    if (getCurrentTime() - lastTimeScreenUpdated > screenUpdateFrequency) {
        updateScreen();
        lastTimeScreenUpdated = getCurrentTime();
    }

<<<<<<< src/WIOTerminal/WIOTerminal.ino
    /* UltrasonicData data;
    data = detectMovement(countMain);
    countMain = data.count; */

    if (getCurrentTime() - lastTimePublished > publishDelayTime) {
        if (mqttLoop()) {
            // publishMessage(PEOPLE_COUNT_TOPIC, String(countMain));
            // publishMessage(TEMPERATURE_TOPIC, String(measureTemperature()));
            // publishMessage(HUMIDITY_TOPIC, String(measureHumidity()));
            publishMessage(LOUDNESS_TOPIC, String(loudnessLevel()));
=======
    if (getCurrentTime() - lastTimePublished > publishDelayTime) {
        if (mqttLoop()) {
            publishMessage(PEOPLE_COUNT_TOPIC, String(getPeople()));
            publishMessage(TEMPERATURE_TOPIC, String(getTemperature()));
            publishMessage(HUMIDITY_TOPIC, String(getHumidity()));
            publishMessage(LOUDNESS_TOPIC, String(getLoudness()));
>>>>>>> src/WIOTerminal/WIOTerminal.ino
            lastTimePublished = getCurrentTime();
        }
    }
}
