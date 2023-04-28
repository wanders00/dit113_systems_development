package com.group6.locusimperium;
public class Utils {
    /**
     * Put the thread to sleep for the time amount set.
     *
     * @param time how long it should delay in milliseconds.
     * @return void
     */
    public static void delay(int time) {
        try {
            Thread.sleep(time);
        } catch(Exception exception) {
            exception.getStackTrace();
        }
    }
}