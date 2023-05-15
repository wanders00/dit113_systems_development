package com.group6.locusimperium;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity implements  NavigationBarView.OnItemSelectedListener {
    public BrokerConnection brokerConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brokerConnection = new BrokerConnection(getApplicationContext());

        // TextView elements setup
        brokerConnection.setPeopleCount(findViewById(R.id.loudness_textview));
        brokerConnection.setTemperatureValue(findViewById(R.id.people_textview));
        brokerConnection.setHumidityValue(findViewById(R.id.temperature_textview));
        brokerConnection.setLoudnessValue(findViewById(R.id.humidity_textview));
        //brokerConnection.setMaxPeopleCount(findViewById(R.id.maxPeopleCount));
        //brokerConnection.setMaxTemperatureValue(findViewById(R.id.maxTemperature));
        //brokerConnection.setMaxHumidityValue(findViewById(R.id.maxHumidity));
        //brokerConnection.setMaxLoudnessValue(findViewById(R.id.maxLoudness));

        // Broker connection
        brokerConnection.connectToMqttBroker();


        // bottom navigation bar selections
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.homeButton);
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.homeButton:
                return true;
            case R.id.connectButton:
                Intent intentLoadSettingsActivity = new Intent(MainActivity.this, ConnectActivity.class);
                startActivity(intentLoadSettingsActivity);
                CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
                return true;
            case R.id.settingsButton:
                Intent intentLoadConnectActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentLoadConnectActivity);
                CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
                return true;
        }

        return false;
    }
}