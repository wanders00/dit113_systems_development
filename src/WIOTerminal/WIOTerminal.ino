#include <Ultrasonic.h>
#include "Arduino.h"
#include <DHT.h>

#include "Ultrasonic.hpp"
#include "Screen.hpp"
#include "TempHumidity.hpp"

int countMain = 0;

int delayTime = 500;

void setup()
{
    screenInit();
    tempHumidInit();
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
}