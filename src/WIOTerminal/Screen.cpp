// Fonts taken from https://github.com/lakshanthad/Wio_Terminal_Classroom_Arduino/blob/main/Classroom%203/Free_Fonts_Example/Free_Fonts.h

#include "Screen.hpp"
#include "TFT_eSPI.h"
#include "Free_Fonts.h"

TFT_eSPI tft;

/**
 * Initialize the screen.
 *
 * @return void
 */
void screenInit() {
    // Screen setup
    tft.begin();
    tft.setRotation(3);

    tft.fillScreen(TFT_WHITE); //background

    tft.setTextColor(TFT_BLUE);
    tft.setFreeFont(FMO24);
    tft.drawString("INIT", 50, 50);
}

/**
 * Display the number of people.
 *
 * @param count Number of people counted.
 * @return void
 */
void displayPeopleCount(int count) {
    tft.fillScreen(TFT_WHITE); //background
    tft.drawString(String(count), 50, 70);
    tft.drawString("People", 50, 120);
}

/**
 * Display the number of people with debug information (measurements from the sensors)
 *
 * @param count Number of people counted.
 * @param dis1 Distance measured by the first sensor.
 * @param dis2 Distance measured by the second sensor.
 * @return void
 */
void displayPeopleCountDebug(int count, int dis1, int dis2) {
    tft.fillScreen(TFT_WHITE); //background
    tft.drawString(String(count), 50, 50);
    tft.drawString(String(dis1), 50, 100);
    tft.drawString(String(dis2), 50, 160);
}