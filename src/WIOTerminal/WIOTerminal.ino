#include <Ultrasonic.h>
#include "Arduino.h"
#include <DHT.h>

#include "Ultrasonic.hpp"
#include "Screen.hpp"
#include "Temp.hpp"

int countMain = 0;

int delayTime = 500;

void setup()
{
    screenInit();
    tempInit();
}
void loop()
{
    UltrasonicData data = detectMovement(countMain);
    countMain = data.count;
    displayPeopleCountDebug(data.count, data.distance1, data.distance2);

    measureTemperature();
    
    delay(delayTime);
}