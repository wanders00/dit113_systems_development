#include <Ultrasonic.h>
#include "Arduino.h"
#include <DHT.h>

#include "Ultrasonic.hpp"
#include "Screen.hpp"
#include "Temp.hpp"
#include "Mqtt.hpp"

int countMain = 0;

int delayTime = 500;

void setup()
{
    screenInit();
    tempInit();
    connectionSetup();
}
void loop()
{
    checkConnection(); // check the MQTT connection
    UltrasonicData data = detectMovement(countMain);
    countMain = data.count;
    // displayPeopleCountDebug(data.count, data.distance1, data.distance2);
    displayPeopleCount(data.count);
    client.publish(TOPIC_Ultrasonic, String(data.count).c_str());

    measureTemperature();
    client.publish(TOPIC_Temperature, String(measureTemperature()).c_str());
    
    client.loop();
    delay(delayTime);
}