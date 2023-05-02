package com.group6.locusimperium;

import static androidx.test.espresso.matcher.ViewMatchers.withId;



import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DisplayTest{

    // test to check if the text elements are being displayed

    @Test
    public void checkConnectionMessage(){
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(withId(R.id.connectionMessage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkPeopleCounterDisplay(){
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(withId(R.id.peopleCount)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkTemperatureDisplay(){
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(withId(R.id.temperatureValue)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkHumidityDisplay(){
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(withId(R.id.humidityValue)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
    
