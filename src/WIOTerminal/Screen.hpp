#ifndef SCREEN_HPP
#define SCREEN_HPP

#include"TFT_eSPI.h"

void screenInit();

void displayPeopleCount(int count);

void displayPeopleCountDebug(int count, int dis1, int dis2);

#endif