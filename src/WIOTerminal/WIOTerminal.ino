#include"TFT_eSPI.h"
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
int delayTime = 500;

void setup()
{
    screenInit();

    mqttInit();

    tempHumidInit();

    initializeLoudness();

    buzzerInit();

    flashScreen();
}
void loop()
{
    UltrasonicData data = detectMovement(countMain);
    countMain = data.count;
    // displayPeopleCountDebug(data.count, data.distance1, data.distance2);
    displayPeopleCount(data.count);
    
    measureTemperature();

    measureHumidity();
    
    delay(delayTime);


    if (mqttLoop())
    {
        UltrasonicData data = detectMovement(countMain);
        countMain = data.count;
        delay(delayTime);
    }
}