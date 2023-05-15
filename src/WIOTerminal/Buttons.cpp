#include <TFT_eSPI.h>
#include "Buttons.hpp"
#include "Settings.hpp"

void buttonsInit(){
    pinMode(WIO_KEY_A, INPUT); //increase button
    pinMode(WIO_KEY_B, INPUT); //decrease button
}