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

int countMain = 0;
int delayTime = 50;

const char *PEOPLE_COUNT_TOPIC = "LocusImperium/WIO/peopleCount";
const char *TEMPRATURE_TOPIC = "LocusImperium/WIO/temperatureValue";
const char *HUMIDITY_TOPIC = "LocusImperium/WIO/humidityValue";
const char *LOUDNESS_TOPIC = "LocusImperium/WIO/loudnessValue";

void setup()
{
    screenInit();

    mqttInit();

    tempHumidInit();

    initializeLoudness();

    flashScreen();
}
void loop()
{
    if (mqttLoop())
    {
        UltrasonicData data = detectMovement(countMain);
        countMain = data.count;
        publishMessage(PEOPLE_COUNT_TOPIC, String(countMain));
        publishMessage(TEMPRATURE_TOPIC, String(measureTemperature()));
        publishMessage(HUMIDITY_TOPIC, String(measureHumidity()));
        publishMessage(LOUDNESS_TOPIC, String(loudnessLevel()));
        delay(delayTime);
    }
}