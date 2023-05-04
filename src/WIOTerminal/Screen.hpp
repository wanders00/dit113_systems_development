#ifndef SCREEN_HPP
#define SCREEN_HPP

#include"TFT_eSPI.h"

void screenInit();

void updateScreen();

void displayPeopleCount(int count);

void displayPeopleCountDebug(int count, int dis1, int dis2);

void displayMessage(String message);

void displayAlert(String alertMessage);

void flashScreen();

#endif