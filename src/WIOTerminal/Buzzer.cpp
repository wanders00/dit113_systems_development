#include "Buzzer.hpp"
#include "Arduino.h"

const u_int16_t BUZZER_PIN = 6;

/**
 * Initialize the buzzer.
 *
 * @return void
 */
void buzzerInit()
{
    pinMode(BUZZER_PIN, OUTPUT);
} 

/**
 * Plays a sound for one second.
 *
 * @return void
 */
void buzz()
{
    // Turn on buzzer
    digitalWrite(BUZZER_PIN, HIGH);

    // "Pause" the method for ~1000 milliseconds
    uint32_t startTime = millis();
    while (true)
    {
        uint32_t currentTime = millis();
        if (currentTime - startTime > 1000) {
            break;
        }
    }

    // Turn off buzzer
    digitalWrite(BUZZER_PIN, LOW);
}