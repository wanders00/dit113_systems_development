// Local header files
#include "Util.hpp"
#include "Arduino.h"

uint32_t currentTime;

/**
 * Forces the method to pause for the delayAmount of time.
 * @note use this over delay() method as this only pauses the method locally.
 *
 * @param delayAmount how long the pause should be in milliseconds.
 * @return void
 */
void timeoutTimer(uint32_t delayAmount) {
    uint32_t startTime = millis();
    while (true) {
        uint32_t currentTime = millis();
        if (currentTime - startTime > delayAmount) {
            break;
        }
    }
}

/**
 * To set the current time of the most recent main loop (WIOTerminal.ino loop() method)
 *
 * @note used so the currentTime does not differentiate due to computation time. Otherwise, see millis()
 * @param time what to set the current time to, note: use millis()
 * @return void
 */
void setCurrentTime(uint32_t time) {
    currentTime = time;
}

/**
 * To get the current time of the most recent main loop (WIOTerminal.ino loop() method).
 * 
 * @note used so the current time does not differentiate due to computation time. Otherwise, see millis()
 * @return uint32_t
 */
uint32_t getCurrentTime() {
    return currentTime;
}