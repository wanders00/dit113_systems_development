#include"TFT_eSPI.h"
#include <Ultrasonic.h>
#include "Arduino.h"

// Initializing the ultrasonic sensors on pins D0 and D2
Ultrasonic ultrasonic1(0);
Ultrasonic ultrasonic2(2);

// Initializing the TFT screen
TFT_eSPI tft;

int count = 0;

int time1 = 0;
int time2 = 0;

int threshHold = 200;
int delayTime = 500;

void setup()
{
    // Screen setup
    tft.begin();
    tft.setRotation(3);

    tft.fillScreen(TFT_WHITE); //background

    tft.setTextColor(TFT_BLUE);
    tft.setTextSize(5);
    tft.drawString("INIT", 50, 50);
}
void loop()
{
    long rangeInCentimeters1;
    long rangeInCentimeters2;

    rangeInCentimeters1 = ultrasonic1.MeasureInCentimeters(); // two measurements should keep an interval
    delay(50);
    rangeInCentimeters2 = ultrasonic2.MeasureInCentimeters();

    // checking if the person is passing
    if(rangeInCentimeters1 < threshHold) {
        time1 = millis();
    }

    if(rangeInCentimeters2 < threshHold) {
        time2 = millis();
    }

    // ! code inspired by https://www.sensingthecity.com/detecting-movement-direction-with-two-ultrasonic-distance-sensors/
    if (time1 > 0 && time2 > 0) {       // if both sensors have nonzero timestamps
        if (time1 < time2) {                      // if left sensor triggered first
            count++;    // direction is left to right
        } 
        else if (time2 < time1) {                 // if right sensor triggered first
            count--;    // direction is right to left
        }

        // after printing direction, reset both timestamps to 0
        time1 = 0;
        time2 = 0;
    }

    if(count < 0){  //prevent the count to go below 0
        count = 0;
    }

    tft.fillScreen(TFT_WHITE); //background
    tft.drawString(String(count), 50, 70);
    tft.drawString(String(rangeInCentimeters1), 50, 120);
    tft.drawString(String(rangeInCentimeters2), 50, 170);
    delay(delayTime);
}