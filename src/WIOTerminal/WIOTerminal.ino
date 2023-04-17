#include <Ultrasonic.h>
#include "Arduino.h"
#include "Ultrasonic.hpp"
#include "Screen.hpp"

int countMain = 0;

int delayTime = 500;

void setup()
{
    initialize();
}
void loop()
{
    UltrasonicData data = detectMovement(countMain);

    countMain = data.count;

    displayPeopleCountDebug(data.count, data.distance1, data.distance2);
    
    delay(delayTime);
}