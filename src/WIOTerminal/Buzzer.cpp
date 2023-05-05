// Local header files
#include "Buzzer.hpp"

#include "Util.hpp"

// General
const u_int16_t BUZZER_PIN = 6;
bool isTurnedOn;

/**
 * Initialize the buzzer.
 *
 * @return void
 */
void buzzerInit() {
    uint32_t turnOffAt = millis();
    pinMode(WIO_BUZZER, OUTPUT);
    // pinMode(BUZZER_PIN, OUTPUT);
    isTurnedOn = false;
}

/**
 * Turn on the buzzer for a small amount of time then turn it off.
 *
 * @return void
 */
void buzzerAlert() {
    turnOnBuzzer();
    timeoutTimer(300);
    turnOffBuzzer();
}

/**
 * Turn on buzzer and sets isTurnedOn to true.
 *
 * @return void
 */
void turnOnBuzzer() {
    digitalWrite(WIO_BUZZER, 150);
    // digitalWrite(BUZZER_PIN, HIGH);
    isTurnedOn = true;
}

/**
 * Turn off buzzer and sets isTurnedOn to false.
 *
 * @return void
 */
void turnOffBuzzer() {
    digitalWrite(WIO_BUZZER, 0);
    //digitalWrite(BUZZER_PIN, LOW);
    isTurnedOn = false;
}
