#include "DHT.h"
#include "TempHumidity.hpp"

#define DHTPIN D4

#define DHTTYPE DHT11

DHT tempHumiditySensor(DHTPIN, DHTTYPE);

void tempHumidInit() {
    Serial.begin(9600);
    tempHumiditySensor.begin();     //Initialize the sensor
}

int measureTemperature() { 
    return tempHumiditySensor.readTemperature(false);    //Measure temperature in celsius
}

int measureHumidity() {
    return tempHumiditySensor.readHumidity();   //Measure humidity
}