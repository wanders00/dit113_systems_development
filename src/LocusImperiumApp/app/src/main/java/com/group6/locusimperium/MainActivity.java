package com.group6.locusimperium;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public BrokerConnection brokerConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brokerConnection = new BrokerConnection(getApplicationContext());

        // TextView elements setup
        brokerConnection.setPeopleCount(findViewById(R.id.peopleCount));
        brokerConnection.setTemperatureValue(findViewById(R.id.temperatureValue));
        brokerConnection.setHumidityValue(findViewById(R.id.humidityValue));
        brokerConnection.setLoudnessValue(findViewById(R.id.loudnessValue));
        //brokerConnection.setMaxPeopleCount(findViewById(R.id.maxPeopleCount));
        //brokerConnection.setMaxTemperatureValue(findViewById(R.id.maxTemperature));
        //brokerConnection.setMaxHumidityValue(findViewById(R.id.maxHumidity));
        //brokerConnection.setMaxLoudnessValue(findViewById(R.id.maxLoudness));

        // Broker connection
        brokerConnection.connectToMqttBroker();

        //check if the app is connected, if not go to the lost connection screen
        Intent connectionCheck = new Intent(MainActivity.this, NoConnectionActivity.class);

        ImageButton goToConnect = (ImageButton) findViewById(R.id.connectButton);
        goToConnect.setOnClickListener(new View.OnClickListener() {
            /**
             * goes to "connect" activity
             * @return void
             */
            @Override
            public void onClick(View view) {
                Intent intentLoadConnectActivity = new Intent(MainActivity.this, ConnectActivity.class);
                startActivity(intentLoadConnectActivity);
            }
        });

        ImageButton goToSetting = (ImageButton) findViewById(R.id.settingsButton);
        goToSetting.setOnClickListener(new View.OnClickListener() {
            /**
             * goes to "settings" activity
             * @return void
             */
            @Override
            public void onClick(View view) {
                Intent intentLoadSettingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentLoadSettingsActivity);

            }
        });

        final Handler handler = new Handler();
        final int delay = 300; // delay between checks in ms

        Runnable runnable = new Runnable() {
            /**
             * checks if the app is connected to the broker, if not go to the lost connection screen
             * @return void
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
}