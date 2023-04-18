package com.group6.locusimperium;

import android.content.Context;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import info.mqtt.android.service.*;

public class MqttClient {

    private MqttAndroidClient mqttClient;

    public MqttClient(Context context, String serverUrl, String clientId) {
        mqttClient = new MqttAndroidClient(context, serverUrl, clientId, Ack.AUTO_ACK);
    }

    public void connect(String username, String password, IMqttActionListener connectionCallback, MqttCallback clientCallback) {

        mqttClient.setCallback(clientCallback);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        try {
            mqttClient.connect(options, null, connectionCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // disconnect from mqtt broker
    public void disconnect(IMqttActionListener disconnectionCallback) {
        try {
            mqttClient.disconnect(null, disconnectionCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // receive information
    public void subscribe(String topic, int qos, IMqttActionListener subscriptionCallback) {
        try {
            mqttClient.subscribe(topic, qos, null, subscriptionCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // unsubscribe from a topic
    public void unsubscribe(String topic, IMqttActionListener unsubscriptionCallback) {
        try {
            mqttClient.unsubscribe(topic, null, unsubscriptionCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // sending information
    public void publish(String topic, String message, int qos, IMqttActionListener publishCallback) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        mqttMessage.setQos(qos);

        try {
            mqttClient.publish(topic, mqttMessage, null, publishCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}