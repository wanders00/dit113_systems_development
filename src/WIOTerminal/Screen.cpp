// Fonts taken from https://github.com/lakshanthad/Wio_Terminal_Classroom_Arduino/blob/main/Classroom%203/Free_Fonts_Example/Free_Fonts.h
#include "fonts/Free_Fonts.h"

// Arduino libraries
#include "TFT_eSPI.h"

// Local header files
#include "MqttClient.hpp"
#include "Screen.hpp"
#include "Settings.hpp"

const uint32_t BACKGROUND_COLOR = TFT_WHITE;
const uint32_t TEXT_COLOR = TFT_BLUE;
const GFXfont *TEXT_FONT_STYLE = FMO12;

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
    tft.setCursor(0, 0);
    tft.fillScreen(BACKGROUND_COLOR);
    tft.setTextColor(TEXT_COLOR);
    tft.setFreeFont(TEXT_FONT_STYLE);
    tft.drawString("SCREEN INIT", 50, 50);
}

/**
 * Screen loop.
 *
 * @note So far only shows wifi/mqtt connection
 * @return void
 */
void updateScreen() {
    flashScreen();
    tft.drawString("Wifi status: " + String(getWifiConnection()), 0, 0);
    tft.drawString("mqtt status: " + String(getMqttConnection()), 0, 30);
}

/**
 * Display the number of people.
 *
 * @param count Number of people counted.
 * @return void
 */
void displayPeopleCount(int count) {
    flashScreen();
    tft.drawString(String(count), 50, 70);
    tft.drawString("People", 50, 120);
}

/**
 * Display the number of people with debug information (measurements from the sensors).
 *
 * @param count Number of people counted.
 * @param dis1 Distance measured by the first sensor.
 * @param dis2 Distance measured by the second sensor.
 * @return void
 */
void displayPeopleCountDebug(int count, int dis1, int dis2) {
    flashScreen();
    tft.drawString(String(count), 50, 50);
    tft.drawString(String(dis1), 50, 100);
    tft.drawString(String(dis2), 50, 160);
}

/**
 * Displays the message argument on the screen.
 *
 * @param message The message to be displayed.
 * @return void
 */
void displayMessage(String message) {
    flashScreen();
    tft.drawString(message, 0, 0);
}

/**
 * Displays the message argument on the screen and sets the background to red.
 *
 * @param alertMessage The message to be displayed.
 * @return void
 */
void displayAlert(String alertMessage) {
    flashScreen();
    tft.fillScreen(TFT_RED);
    tft.drawString(alertMessage, 0, 0);
}

/**
 * Removes all content on the screen.
 *
 * @return void
 * @note calls tft.fillScreen(TFT_WHITE)
 */
void flashScreen() {
    tft.fillScreen(TFT_WHITE);
}