package com.group6.locusimperium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
public class NoConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);

        Button reconnect = (Button) findViewById(R.id.reconnectButton);
        reconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGoToConnectionActivity = new Intent(NoConnectionActivity.this, ConnectActivity.class);
                startActivity(intentGoToConnectionActivity);
            }
        });

        BrokerConnection brokerConnection = new BrokerConnection(getApplicationContext());
        final Handler handler = new Handler();
        final int delay = 300; // delay between checks in ms

        Runnable runnable = new Runnable() {
            /**
             * checks if the app is connected, if not go to the lost connection screen
             * @return void
             */
            @Override
            public void run() {
                if (brokerConnection.getConnectionStatus()) {
                    Intent intent = new Intent(NoConnectionActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    handler.postDelayed(this, delay);
                }
            }
        };

        handler.postDelayed(runnable, delay);

    }
}