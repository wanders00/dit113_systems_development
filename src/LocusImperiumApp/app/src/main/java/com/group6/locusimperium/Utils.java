package com.group6.locusimperium;
public class Utils {
    public static void delay(int time){
        try{
            Thread.sleep(time);
        }catch(Exception exception){
            exception.getStackTrace();
        }
    }
}
