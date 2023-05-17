package com.group6.locusimperium;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.group6.locusimperium.SettingsActivity.SHARED_PREFS;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import maes.tech.intentanim.CustomIntent;

public class ConnectActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private Button saveIPButton;
    private EditText inputIP;
    private String ipaddress;
    public static final String IPADDRESS = "ipaddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        inputIP = findViewById(R.id.inputIPAddress);
        saveIPButton = findViewById(R.id.saveIP);
        saveIPButton.setOnClickListener(new View.OnClickListener() {
            /**
             * saves data when save button is clicked
             */
            @Override
            public void onClick(View view) {
                saveIP();
            }
        });

        loadIP();
        updateIP();

        // bottom navigation bar selections
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.connectButton);
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeButton:
                Intent intentLoadMainActivity = new Intent(ConnectActivity.this, MainActivity.class);
                startActivity(intentLoadMainActivity);
                CustomIntent.customType(ConnectActivity.this, "fadein-to-fadeout");
                return true;
            case R.id.connectButton:
                return true;
            case R.id.settingsButton:
                Intent intentLoadSettingsActivity = new Intent(ConnectActivity.this, SettingsActivity.class);
                startActivity(intentLoadSettingsActivity);
                CustomIntent.customType(ConnectActivity.this, "fadein-to-fadeout");
                return true;
        }

        return false;
    }

    /**
     * saves IP address to shared preferences and disconnect from broker to reconnect with new IP
     */
    public void saveIP() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IPADDRESS, inputIP.getText().toString());
        editor.apply();
        App globalApp = (App) getApplicationContext();
        globalApp.getBrokerConnection().getMqttClient().disconnect(null);
        Toast.makeText(this, "Saved IP", Toast.LENGTH_SHORT).show();
    }

    public void loadIP() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        ipaddress = sharedPreferences.getString(IPADDRESS, "");
    }

    public void updateIP() {
        inputIP.setText(ipaddress);
    }

}
