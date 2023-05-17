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
#include "Settings.hpp"
#include "TempHumidity.hpp"
#include "Ultrasonic.hpp"
#include "Util.hpp"
#include "WifiDetails.h"
#include "Settings.hpp"
#include "Buttons.hpp"
#include "Buttons.hpp"

// Ultrasonic
int countMain = 0;

// Screen update
const uint32_t screenUpdateFrequency = 1000;
uint32_t lastTimeScreenUpdated;

// MQTT publish frequency values
const uint32_t publishDelayTime = 3000;
uint32_t lastTimePublished;

// MQTT subscription topics
const char *ROOM_DATA_PUBLISH_TOPIC = "LocusImperium/WIO/roomData";


void setup() {
    screenInit();

    mqttInit();

    tempHumidInit();

    loudnessInit();

    buzzerInit();

    buttonsInit();

    buttonsInit();

    lastTimePublished = 0;
    lastTimeScreenUpdated = 0;

    setMaxPeople(10);
    setMaxTemperature(30);
    setMaxHumidity(30);
    setMaxLoudness(50);
}

void loop() {
    setCurrentTime(millis());
    buzzerLoop();

    UltrasonicData data;
    data = detectMovement(getPeople());

    setPeople(data.count);
    setTemperature(measureTemperature());
    setHumidity(measureHumidity());
    setLoudness(loudnessMapped());

    if(digitalRead(WIO_KEY_A) == LOW) {
        setPeople(getPeople() + 1);
    }

    if(digitalRead(WIO_KEY_B) == LOW) {
        if(getPeople() > 0){
            setPeople(getPeople() - 1);
        }else{setPeople(0); }
    }

    if (getCurrentTime() - lastTimeScreenUpdated > screenUpdateFrequency) {
        updateScreen();
        lastTimeScreenUpdated = getCurrentTime();
    }

    if (getCurrentTime() - lastTimePublished > publishDelayTime) {
        if (mqttLoop()) {
            publishMessage(ROOM_DATA_PUBLISH_TOPIC, String(getPeople()) + ',' + String(getHumidity()) + ',' + String(getTemperature()) + ',' + String(getLoudness()));
            lastTimePublished = getCurrentTime();
        }
    }
}

