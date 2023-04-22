#include "DHT.h"
#include "TempHumidity.hpp"

#define DHTPIN D4

#define DHTTYPE DHT11

DHT tempHumiditySensor(DHTPIN, DHTTYPE);

/// @brief Initialise the temperature and humidity sensor
void tempHumidInit() {
    Serial.begin(9600);
    tempHumiditySensor.begin();
}

/// @brief Measure temperature in celsius
/// @return Temperature as an integer
int measureTemperature() { 
    return tempHumiditySensor.readTemperature(false);
}

/// @brief Measures humidity
/// @return Humidity as an integer in a scale from 0 to 100
int measureHumidity() {
    return tempHumiditySensor.readHumidity(); 
}