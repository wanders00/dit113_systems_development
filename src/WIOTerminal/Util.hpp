#ifndef UTIL_HPP
#define UTIL_HPP

// Local header files
#include "Arduino.h"

void timeoutTimer(uint32_t delayAmount);

void setCurrentTime(uint32_t time);

uint32_t getCurrentTime();

#endif