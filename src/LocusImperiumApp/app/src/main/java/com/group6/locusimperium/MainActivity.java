package com.group6.locusimperium;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    public BrokerConnection brokerConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App globalApp = (App) getApplicationContext();
        brokerConnection = globalApp.getBrokerConnection();

        // TextView elements setup
        brokerConnection.setPeopleCount(findViewById(R.id.loudness_textview));
        brokerConnection.setTemperatureValue(findViewById(R.id.people_textview));
        brokerConnection.setHumidityValue(findViewById(R.id.temperature_textview));
        brokerConnection.setLoudnessValue(findViewById(R.id.humidity_textview));

        // Broker connection
        brokerConnection.connectToMqttBroker();

        // bottom navigation bar selections
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.homeButton);
        bottomNavigationView.setOnItemSelectedListener(this);

        final Handler handler = new Handler();
        final int delay = 1000; // delay between checks in ms

        Runnable runnable = new Runnable() {
            /**
             * checks if the app is connected to the broker, if not go to the lost connection screen
             */
            @Override
            public void run() {
                if (!brokerConnection.getConnectionStatus()) {
                    Intent intent = new Intent(MainActivity.this, NoConnectionActivity.class);
                    startActivity(intent);
                } else {
                    handler.postDelayed(this, delay);
                }
            }
        };

        handler.postDelayed(runnable, delay);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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