package com.group6.locusimperium;

import android.app.Application;

public class App extends Application {
    private BrokerConnection brokerConnection;

    @Override
    public void onCreate() {
        super.onCreate();
        brokerConnection = new BrokerConnection(getApplicationContext());
    }

    BrokerConnection getBrokerConnection() {
        return brokerConnection;
    }
}
