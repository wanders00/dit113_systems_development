package com.group6.locusimperium;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import static com.group6.locusimperium.SettingsActivity.SHARED_PREFS;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ConnectActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    Context context;
    private Button saveIPButton;
    private EditText inputIP;
    private String ipaddress;
    public static final String IPADDRESS = "ipaddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        inputIP = (EditText) findViewById(R.id.inputIPAddress);
        saveIPButton = (Button) findViewById(R.id.saveIP);
        saveIPButton.setOnClickListener(new View.OnClickListener() {
            /**
             * saves data when save button is clicked
             * @return void
             */
            @Override
            public void onClick(View view) {
                saveIP();
            }
        });

        loadIP();
        updateIP();

        // bottom navigation bar selections
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.connectButton);
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.homeButton:
                Intent intentLoadMainActivity = new Intent(ConnectActivity.this, MainActivity.class);
                startActivity(intentLoadMainActivity);
            case R.id.connectButton:
                return true;
            case R.id.settingsButton:
                Intent intentLoadSettingsActivity = new Intent(ConnectActivity.this, SettingsActivity.class);
                startActivity(intentLoadSettingsActivity);
                return true;
        }

        return false;
    }

    /**
     * saves IP address to shared preferences and disconnect from broker to reconnect with new IP
     * @return void
     */
    public void saveIP() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        BrokerConnection brokerConnection = new BrokerConnection(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IPADDRESS, inputIP.getText().toString());
        editor.apply();
        brokerConnection.getMqttClient().disconnect(null);
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
