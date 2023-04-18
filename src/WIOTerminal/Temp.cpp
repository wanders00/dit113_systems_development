#include "DHT.h"
#include "Temp.hpp"

#define DHTPIN D4

#define DHTTYPE DHT11

DHT tempSensor(DHTPIN, DHTTYPE);

void tempInit() {
    Serial.begin(9600);
    tempSensor.begin();     //Initialize the sensor
}

int measureTemperature() { 
    return tempSensor.readTemperature(false);    //Measure temperature in celsius
}
int measureTemperature() { 
    return tempSensor.readTemperature(false);    //Measure temperature in celsius
}