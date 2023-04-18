package com.group6.locusimperium;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BrokerConnection brokerConnection;
    public static final String PUB_TOPIC = "LocusImperium/APP/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brokerConnection = new BrokerConnection(getApplicationContext());

        brokerConnection.setConnectionMessage(findViewById(R.id.connectionMessage)); // TODO: DELETE THIS, TEMP VALUE
        // TextView elements setup
        brokerConnection.setPeopleCount(findViewById(R.id.peopleCount));
        brokerConnection.setTemperatureValue(findViewById(R.id.temperatureValue));
        brokerConnection.setHumidityValue(findViewById(R.id.humidityValue));
        brokerConnection.setLoudnessValue(findViewById(R.id.loudnessValue));
        brokerConnection.setMaxPeopleCount(findViewById(R.id.maxPeopleCount));
        brokerConnection.setMaxTemperatureValue(findViewById(R.id.maxTemperature));
        brokerConnection.setMaxHumidityValue(findViewById(R.id.maxHumidity));
        brokerConnection.setMaxLoudnessValue(findViewById(R.id.maxLoudness));

        // Broker connection
        brokerConnection.connectToMqttBroker();
    }

}
