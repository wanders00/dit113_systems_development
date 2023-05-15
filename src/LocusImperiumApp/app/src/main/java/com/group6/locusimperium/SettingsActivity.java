package com.group6.locusimperium;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import maes.tech.intentanim.CustomIntent;

public class SettingsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public BrokerConnection brokerConnection;
    private String [] item = {"Quiet", "Moderate", "Loud"};
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    private EditText inputPeople;
    private EditText inputTemp;
    private EditText inputHumidity;
    private Button saveButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String PEOPLE = "people";
    public static final String TEMPERATURE = "temperature";
    public static final String HUMIDITY = "humidity";
    public static final String LOUDNESS = "loudness";

    private String people;
    private String temperature;
    private String humidity;
    private String loudness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        brokerConnection = new BrokerConnection(getApplicationContext());
        brokerConnection.connectToMqttBroker();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        String peopleValue = sharedPreferences.getString("people", null);
        String temperatureValue = sharedPreferences.getString("temperature", null);
        String humidityValue = sharedPreferences.getString("humidity", null);
        String loudnessValue = sharedPreferences.getString("loudness", null);

        inputTemp = (EditText) findViewById(R.id.inputTemp);
        inputHumidity = (EditText) findViewById(R.id.inputHumidity);
        inputPeople = (EditText) findViewById(R.id.inputPeople);
        saveButton = (Button) findViewById(R.id.savebutton);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * when item is clicked open a menu with options from the "item" array
             * @return void
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item= adapterView.getItemAtPosition(i).toString();
                Toast.makeText(SettingsActivity.this, "Item:" + item, Toast.LENGTH_SHORT).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            /**
             * saves data when save button is clicked
             * @return void
             */
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        loadData();
        updateViews();
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, item);
        autoCompleteTextView.setAdapter(adapterItems);

        // bottom navigation bar selections
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.settingsButton);
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.connectButton:
                Intent intentLoadConnectActivity = new Intent(SettingsActivity.this, ConnectActivity.class);
                startActivity(intentLoadConnectActivity);
                CustomIntent.customType(SettingsActivity.this, "fadein-to-fadeout");
                return true;
            case R.id.homeButton:
                Intent intentLoadMainActivity = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intentLoadMainActivity);
                CustomIntent.customType(SettingsActivity.this, "fadein-to-fadeout");
                return true;
            case R.id.settingsButton:
                return true;
        }

        return false;
    }

    /**
     * saves data to shared preferences and publishing the settings to the broker.
     * @return void
     */
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HUMIDITY, inputHumidity.getText().toString());
        editor.putString(TEMPERATURE, inputTemp.getText().toString());
        editor.putString(PEOPLE, inputPeople.getText().toString());
        editor.putString(LOUDNESS,autoCompleteTextView.getText().toString());
        editor.apply();
        brokerConnection.publishSettings();


        Toast.makeText(this,"Data Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * loads data from shared preferences
     * @return void
     */
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        people = sharedPreferences.getString(PEOPLE,"");
        temperature = sharedPreferences.getString(TEMPERATURE,"");
        humidity = sharedPreferences.getString(HUMIDITY,"");
        loudness = sharedPreferences.getString(LOUDNESS,"");
    }
    /**
     * updates the views with the data from shared preferences
     * @return void
     */
    public void updateViews() {
        inputTemp.setText(temperature);
        inputHumidity.setText(humidity);
        inputPeople.setText(people);
        autoCompleteTextView.setText(loudness);
    }
}