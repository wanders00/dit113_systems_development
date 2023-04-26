/****************************************************************************
 Code base on "DIT113MqttWorkshop".
 Author: Nicole Quinstedt
 Source: https://github.com/Quinstedt/DIT113MqttWorkshop/blob/main/SpeechToText/app/src/main/java/com/quinstedt/speechtotext/BrokerConnection.java
 *****************************************************************************/

package com.group6.locusimperium;

import static com.group6.locusimperium.MainActivity.PUB_TOPIC;

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
    public static final String LOCALHOST = "192.168.251.91";
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

    /**
     * Establishes connection to the mqtt broker.
     * @see MqttClient
     */
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
                 * @see ValueSubtopic
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

    /**
     * Publishes a message to the mqtt broker. Topic defined in MainActivity.java
     * @param message the payload of the message.
     * @param actionDescription what caused the publish.
     * @return void
     * @see MainActivity
     */
    public void publishMqttMessage(String message, String actionDescription) {
        if (!isConnected) {
            final String notConnected = "Not connected (yet)";
            Log.e(CLIENT_ID, notConnected);
            Toast.makeText(context, notConnected, Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(CLIENT_ID, actionDescription);
        mqttClient.publish(PUB_TOPIC, message, QOS, null);
    }

    // Methods to link TextView object to actual element on the screen on startup.

    /**
     * Updates the text of the connectionMessage TextView.
     * @param textView the new text
     */
    public void setConnectionMessage(TextView textView) {
        this.connectionMessage = textView;
    }

    /**
     * Updates the text of the peopleCount TextView.
     * @param textView the new text
     */
    public void setPeopleCount(TextView textView) {
        this.peopleCount = textView;
    }

    /**
     * Updates the text of the temperatureValue TextView.
     * @param textView the new text
     */
    public void setTemperatureValue(TextView textView) {
        this.temperatureValue = textView;
    }

    /**
     * Updates the text of the humidityValue TextView.
     * @param textView the new text
     */
    public void setHumidityValue(TextView textView) {
        this.humidityValue = textView;
    }

    /**
     * Updates the text of the loudnessValue TextView.
     * @param textView the new text
     */
    public void setLoudnessValue(TextView textView) { this.loudnessValue = textView; }

    /**
     * Updates the text of the maxPeopleCount TextView.
     * @param textView the new text
     */
    public void setMaxPeopleCount(TextView textView) {
        this.maxPeopleCount = textView;
    }

    /**
     * Updates the text of the maxTemperatureValue TextView.
     * @param textView the new text
     */
    public void setMaxTemperatureValue(TextView textView) {
        this.maxTemperatureValue = textView;
    }

    /**
     * Updates the text of the maxHumidityValue TextView.
     * @param textView the new text
     */
    public void setMaxHumidityValue(TextView textView) {
        this.maxHumidityValue = textView;
    }

    /**
     * Updates the text of the maxLoudnessValue TextView.
     * @param textView the new text
     */
    public void setMaxLoudnessValue(TextView textView) { this.maxLoudnessValue = textView; }

    /**
     * Gets the corresponding MqttClient object of the BrokerConnection object.
     * @return MqttClient object
     */
    public MqttClient getMqttClient() {
        return mqttClient;
    }

}
