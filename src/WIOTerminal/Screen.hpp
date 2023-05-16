#ifndef SCREEN_HPP
#define SCREEN_HPP

// Arduino libraries
#include <TFT_eSPI.h>

// Local header files
#include "MqttClient.hpp"
#include "Screen.hpp"
#include "Settings.hpp"
#include "Buzzer.hpp"

void screenInit();

void updateScreen();

void startUpImg();

void startUpImg(String displayMessage);

void displayAlert();

#endif