#include "Loudness.hpp"
#include "Arduino.h"

void initializeLoudness() {
    pinMode(WIO_MIC, INPUT);
}

/**
 * Get the raw value of the loudness sensor.
 * 
 * @return int The raw value of the loudness sensor.
 */
int loudnessRaw() {
  int val = analogRead(WIO_MIC);
  return val;
}

/**
 * Get the loudness level mapped from 0 to 100.
 * 
 * @return int The loudness level.
 */
int loudnessMapped() {
    int val = loudnessRaw();
    int level = map(val, 0, 1023, 0, 100);
    return level;
}

/**
 * Get the loudness level mapped from 0 to 3.
 * 
 * @return int The loudness level.
 */
int loudnessLevel() {
    int val = loudnessMapped();

    /*
    Quiet - 460
    Normal - 500
    Loud - 600
    */ 
   
    if (val < 460) {
        return 0;
    } else if (val < 500) {
        return 1;
    } else if (val < 600) {
        return 2;
    } else {
        return 3;
    }
}