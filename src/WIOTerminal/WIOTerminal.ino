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
<<<<<<< src/WIOTerminal/WIOTerminal.ino
#include "Buttons.hpp"
#include "Buttons.hpp"
=======
>>>>>>> src/WIOTerminal/WIOTerminal.ino

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

<<<<<<< src/WIOTerminal/WIOTerminal.ino
    lastTimePublished = 0;
    lastTimeScreenUpdated = 0;
=======
>>>>>>> src/WIOTerminal/WIOTerminal.ino
    setMaxPeople(10);
    setMaxTemperature(30);
    setMaxHumidity(30);
    setMaxLoudness(50);
}

void loop() {
    setCurrentTime(millis());
<<<<<<< src/WIOTerminal/WIOTerminal.ino
    
=======

>>>>>>> src/WIOTerminal/WIOTerminal.ino
    buzzerLoop();

    UltrasonicData data;
    data = detectMovement(getPeople());

    setPeople(data.count);
    setTemperature(measureTemperature());
    setHumidity(measureHumidity());
    setLoudness(loudnessMapped());

<<<<<<< src/WIOTerminal/WIOTerminal.ino
    if(digitalRead(WIO_KEY_A) == LOW) {
        setPeople(getPeople() + 1);
    }

    if(digitalRead(WIO_KEY_B) == LOW) {
        if(getPeople() > 0){
            setPeople(getPeople() - 1);
        }else{setPeople(0); }
    }

=======
>>>>>>> src/WIOTerminal/WIOTerminal.ino
    if (getCurrentTime() - lastTimeScreenUpdated > screenUpdateFrequency) {
        updateScreen();
        lastTimeScreenUpdated = getCurrentTime();
    }

<<<<<<< src/WIOTerminal/WIOTerminal.ino
    if (getCurrentTime() - lastTimePublished > publishDelayTime) {
        if (mqttLoop()) {
            publishMessage(PEOPLE_COUNT_TOPIC, String(getPeople()));
            publishMessage(TEMPERATURE_TOPIC, String(getTemperature()));
            publishMessage(HUMIDITY_TOPIC, String(getHumidity()));
            publishMessage(LOUDNESS_TOPIC, String(getLoudness()));
=======
    /* UltrasonicData data;
    data = detectMovement(countMain);
    countMain = data.count; */

    if (getCurrentTime() - lastTimePublished > publishDelayTime) {
        if (mqttLoop()) {
            publishMessage(ROOM_DATA_PUBLISH_TOPIC, String(getPeople()) + ',' + String(getHumidity()) + ',' + String(getTemperature()) + ',' + String(getLoudness()));
>>>>>>> src/WIOTerminal/WIOTerminal.ino
            lastTimePublished = getCurrentTime();
        }
    }
}
