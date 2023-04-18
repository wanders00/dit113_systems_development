package com.group6.locusimperium;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class BrokerConnection extends AppCompatActivity {

    //Application subscription, will receive everything from this topic.
    public static final String SUPER_SUBSCRIPTION_TOPIC = "LocusImperium/WIO/";
    public static final String LOCALHOST = "10.0.2.2";
    private static final String MQTT_SERVER = "tcp://" + LOCALHOST + ":1883";
    public static final String CLIENT_ID = "LocusImperium-Application";
    public static final int QOS = 0;
    private boolean isConnected = false;
    private MqttClient mqttClient;
    Context context;

    //TextView elements
    public TextView connectionMessage; // TODO: DELETE THIS
    public TextView peopleCount;
    public TextView temperatureValue;
    public TextView humidityValue;
    public TextView loudnessValue;

    //TextView max-value elements
    public TextView maxPeopleCount;
    public TextView maxTemperatureValue;
    public TextView maxHumidityValue;
    public TextView maxLoudnessValue;

    public BrokerConnection(Context context) {
        this.context = context;
        mqttClient = new MqttClient(context, MQTT_SERVER, CLIENT_ID);
        connectToMqttBroker();
    }

    public void connectToMqttBroker() {
        if (!isConnected) {
            mqttClient.connect(CLIENT_ID, "", new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    isConnected = true;
                    final String successfulConnection = "Connected to MQTT broker";
                    Log.i(CLIENT_ID, successfulConnection);
                    Toast.makeText(context, successfulConnection, Toast.LENGTH_LONG).show();
                    // Added "+ '#'" to subscribe to all subtopics under the super one.
                    mqttClient.subscribe(SUPER_SUBSCRIPTION_TOPIC + '#', QOS, null);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    final String failedConnection = "Failed to connect to MQTT broker";
                    Log.e(CLIENT_ID, failedConnection);
                    Toast.makeText(context, failedConnection, Toast.LENGTH_LONG).show();
                }
            }, new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    isConnected = false;

                    final String connectionLost = "Connection to MQTT broker lost";
                    Log.w(CLIENT_ID, connectionLost);
                    Toast.makeText(context, connectionLost, Toast.LENGTH_LONG).show();
                }
                /**
                 * @param topic The topic of the mqtt message.
                 * @param message The message of the mqtt message.
                 */
                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    String messageMQTT = message.toString();
                    ValueSubtopic currentTopic = ValueSubtopic.whichSubTopic(topic);
                    if(currentTopic != null) {
                        switch(currentTopic) {
                            case PEOPLE_COUNT:
                                peopleCount.setText(messageMQTT);
                                break;
                            case TEMPERATURE_VALUE:
                                temperatureValue.setText(messageMQTT);
                                break;
                            case HUMIDITY_VALUE:
                                humidityValue.setText(messageMQTT);
                                break;
                            case LOUDNESS_VALUE:
                                loudnessValue.setText(messageMQTT);
                                break;
                            case MAX_PEOPLE_COUNT:
                                maxPeopleCount.setText(messageMQTT);
                                break;
                            case MAX_TEMPERATURE:
                                maxTemperatureValue.setText(messageMQTT);
                                break;
                            case MAX_HUMIDITY:
                                maxHumidityValue.setText(messageMQTT);
                                break;
                            case MAX_LOUDNESS:
                                maxLoudnessValue.setText(messageMQTT);
                                break;
                        }
                    } else {
                        connectionMessage.setText(messageMQTT);
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(CLIENT_ID, "Message delivered");
                }
            });
        }
    }

    // Methods to link TextView object to actual element on the screen on startup.
    public void setConnectionMessage(TextView textView) {
        this.connectionMessage = textView;
    }
    public void setPeopleCount(TextView textView) {
        this.peopleCount = textView;
    }
    public void setTemperatureValue(TextView textView) {
        this.temperatureValue = textView;
    }
    public void setHumidityValue(TextView textView) {
        this.humidityValue = textView;
    }
    public void setLoudnessValue(TextView textView) { this.loudnessValue = textView; }
    public void setMaxPeopleCount(TextView textView) {
        this.maxPeopleCount = textView;
    }
    public void setMaxTemperatureValue(TextView textView) {
        this.maxTemperatureValue = textView;
    }
    public void setMaxHumidityValue(TextView textView) {
        this.maxHumidityValue = textView;
    }
    public void setMaxLoudnessValue(TextView textView) { this.maxLoudnessValue = textView; }

    /**
     * @param message           - the message that we send to the broker
     */
    public void publishMqttMessage(String message, String actionDescription) {
        if (!isConnected) {
            final String notConnected = "Not connected (yet)";
            Log.e(CLIENT_ID, notConnected);
            Toast.makeText(context, notConnected, Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(CLIENT_ID, actionDescription);
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

}
