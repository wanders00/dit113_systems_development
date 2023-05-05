#ifndef LOUDNESS_HPP
#define LOUDNESS_HPP

#include "Arduino.h"

void loudnessInit();

int loudnessRaw();

int loudnessMapped();

int loudnessLevel();

#endif