/**
 * Tests to check if the text and visual elements of the different screens are being displayed
 */

package com.group6.locusimperium;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import static org.junit.Assert.fail;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.EspressoKey;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class DisplayTest{
    // test to check if the text and visual elements are being displayed
    @Test
    public void checkPeopleCounterDisplay(){
        BrokerConnection.setConnectionStatus(true);
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(withId(R.id.peopleCount)).check(matches(isDisplayed())); //check if the counter is displayed
        Espresso.onView(withId(R.id.grayPerson)).check(matches(isDisplayed())); //check if the icon is displayed

    }
    @Test
    public void checkTemperatureDisplay(){
        BrokerConnection.setConnectionStatus(true);
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(withId(R.id.temperatureValue)).check(matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.temperature)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkHumidityDisplay(){
        BrokerConnection.setConnectionStatus(true);
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(withId(R.id.humidityValue)).check(matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.humidity)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkSettingsButton(){
        BrokerConnection.setConnectionStatus(true);
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(withId(R.id.settingsButton)).check(matches(ViewMatchers.isDisplayed())); //check if the settings button is displayed
        Espresso.onView(withId(R.id.settingsButton)).perform(ViewActions.click()); //click the settings button
        Espresso.onView(withId(R.id.settings)).check(matches(ViewMatchers.isDisplayed()));
    }
    @Test
    public void CheckConnectButton(){
        BrokerConnection.setConnectionStatus(true);
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(withId(R.id.connectButton)).check(matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.connectButton)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.connect)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkNoConnectionScreen(){
        BrokerConnection.setConnectionStatus(false);
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(withId(R.id.noConnectionAct)).check(matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.noConnectionMsg)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkReconnectButton(){
        BrokerConnection.setConnectionStatus(false);
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        Espresso.onView(withId(R.id.reconnectButton)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.connect)).check(matches(ViewMatchers.isDisplayed()));
    }

}
    
