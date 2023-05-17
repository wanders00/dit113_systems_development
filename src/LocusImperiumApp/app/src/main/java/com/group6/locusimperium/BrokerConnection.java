/****************************************************************************
 Code base on "DIT113MqttWorkshop".
 Author: Nicole Quinstedt
 Source: https://github.com/Quinstedt/DIT113MqttWorkshop/blob/main/SpeechToText/app/src/main/java/com/quinstedt/speechtotext/BrokerConnection.java
 *****************************************************************************/

package com.group6.locusimperium;

import static com.group6.locusimperium.ConnectActivity.IPADDRESS;
import static com.group6.locusimperium.SettingsActivity.HUMIDITY;
import static com.group6.locusimperium.SettingsActivity.LOUDNESS;
import static com.group6.locusimperium.SettingsActivity.PEOPLE;
import static com.group6.locusimperium.SettingsActivity.SHARED_PREFS;
import static com.group6.locusimperium.SettingsActivity.TEMPERATURE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
    // Global predefined values
    private static final String SUBSCRIPTION_TOPIC = "LocusImperium/WIO/";
    private static final String PUBLISH_TOPIC = "LocusImperium/APP/";
    private static final String MAX_SETTINGS_PUBLISH_TOPIC = PUBLISH_TOPIC + "maxSettings";
    private static final String CLIENT_ID = "LocusImperium-Application";
    private static final int QOS = 0; // For mqtt messaging; which quality of service to use.

    //Attributes
    private boolean isConnected = false;
    private MqttClient mqttClient;
    private Context context;

    //TextView elements to update on MainActivity
    public TextView peopleCount;
    public TextView temperatureValue;
    public TextView humidityValue;
    public TextView loudnessValue;

    public BrokerConnection(Context context) {
        this.context = context;
        connectToMqttBroker();
    }

    /**
     * Establishes connection to the mqtt broker.
     *
     * @see MqttClient
     */
    public void connectToMqttBroker() {
        if (!isConnected) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            String LOCALHOST = sharedPreferences.getString(IPADDRESS, "");
            String MQTT_SERVER = "tcp://" + LOCALHOST + ":1883";
            mqttClient = new MqttClient(context, MQTT_SERVER, CLIENT_ID);

            mqttClient.connect(CLIENT_ID, "", new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    isConnected = true;
                    final String successfulConnection = "Connected to MQTT broker";
                    Log.i(CLIENT_ID, successfulConnection);
                    // display a snack-bar to show that the IP address has been saved
                    View contextView = findViewById(R.id.connect);
                    Snackbar.make(contextView, "Connected!", Snackbar.LENGTH_SHORT).setAnchorView(R.id.bottom_navigation).show();
                    // Added "+ '#'" to subscribe to all subtopics under the super one.
                    mqttClient.subscribe(SUBSCRIPTION_TOPIC + '#', QOS, null);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    final String failedConnection = "Failed to connect to MQTT broker";
                    // display a snack-bar to show that the IP address has been saved
                    View contextView = findViewById(R.id.connect);
                    Snackbar.make(contextView, "Connection failed!", Snackbar.LENGTH_SHORT).setAnchorView(R.id.bottom_navigation).show();
                    Log.e(CLIENT_ID, failedConnection);
                }
            }, new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    isConnected = false;

                    final String connectionLost = "Connection to MQTT broker lost";
                    Log.w(CLIENT_ID, connectionLost);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    String messageMQTT = message.toString();
                    String[] topicArray = messageMQTT.split(",");
                    //The position of the different settings values are predefined in the array.
                    peopleCountArrived(topicArray[0]);
                    humidityValueArrived(topicArray[1]);
                    temperatureValueArrived(topicArray[2]);
                    loudnessValueArrived(topicArray[3]);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(CLIENT_ID, "Message delivered");
                }
            });
        }
    }

    /**
     * updates the people counter textview value, color is gray if the value is below the max value, red if above. The max value is set in the settings.
     */
    public void peopleCountArrived(String message) {
        peopleCount.setText(message);
        if (Integer.parseInt(message) > Integer.parseInt(getMaxPeople())) {
            peopleCount.setText(message);
            peopleCount.setTextColor(Color.RED);
        } else {
            peopleCount.setText(message);
            peopleCount.setTextColor(Color.GRAY);
        }
    }

    /**
     * updates the humidity textview value, color is gray if the value is below the max value, red if above. The max value is set in the settings.
     */
    public void humidityValueArrived(String message) {
        humidityValue.setText(message);
        if (Integer.parseInt(message) > Integer.parseInt(getMaxHumidity())) {
            humidityValue.setText(message);
            humidityValue.setTextColor(Color.RED);
        } else {
            humidityValue.setText(message);
            humidityValue.setTextColor(Color.GRAY);
        }
    }

    /**
     * updates the temperature textview value, color is gray if the value is below the max value, red if above. The max value is set in the settings.
     */
    public void temperatureValueArrived(String message) {
        temperatureValue.setText(message);
        if (Integer.parseInt(message) > Integer.parseInt(getMaxTemperature())) {
            temperatureValue.setText(message);
            temperatureValue.setTextColor(Color.RED);
        } else {
            temperatureValue.setText(message);
            temperatureValue.setTextColor(Color.GRAY);
        }
    }

    /**
     * updates the loudness textview value, color is gray if the value is below the max value, red if above. The max value is set in the settings.
     */
    public void loudnessValueArrived(String message) {
        int loudness = 0;
        if (getMaxLoudness().equals("Quiet")) {
            loudness = 50;
        } else if (getMaxLoudness().equals("Moderate")) {
            loudness = 60;
        } else if (getMaxLoudness().equals("Loud")) {
            loudness = 70;
        }

        if (Integer.parseInt(message) < loudness) {
            loudnessValue.setText(message);
            loudnessValue.setTextColor(Color.GRAY);
        } else {
            loudnessValue.setText(message);
            loudnessValue.setTextColor(Color.RED);
        }
    }

    /**
     * Publishes the settings to the broker.
     */
    public void publishSettings() {
        if (!isConnected) {
            final String notConnected = "Not connected (yet)";
            Log.e(CLIENT_ID, notConnected);
            //Toast.makeText(context, notConnected, Toast.LENGTH_SHORT).show();
            return;
        } else {
            final String connected = "Connected";
            Log.e(CLIENT_ID, connected);
            mqttClient.publish(MAX_SETTINGS_PUBLISH_TOPIC, getMaxPeople() + "," + getMaxHumidity() + "," + getMaxTemperature() + "," + getMaxLoudness(), QOS, null);
        }
    }

    /**
     * Gets the corresponding MqttClient object of the BrokerConnection object.
     *
     * @return MqttClient object
     */
    public MqttClient getMqttClient() {
        return mqttClient;
    }

    // Methods to link TextView object to actual element on the screen on startup.

    /**
     * Updates the text of the peopleCount TextView.
     *
     * @param textView the new text
     */
    public void setPeopleCount(TextView textView) {
        this.peopleCount = textView;
    }

    /**
     * Updates the text of the temperatureValue TextView.
     *
     * @param textView the new text
     */
    public void setTemperatureValue(TextView textView) {
        this.temperatureValue = textView;
    }

    /**
     * Updates the text of the humidityValue TextView.
     *
     * @param textView the new text
     */
    public void setHumidityValue(TextView textView) {
        this.humidityValue = textView;
    }

    /**
     * Updates the text of the loudnessValue TextView.
     *
     * @param textView the new text
     */
    public void setLoudnessValue(TextView textView) {
        this.loudnessValue = textView;
    }

    // Helper methods to calculate if current readings are above maximum values.

    /**
     * Gets the maximumed allowed of people from the SharedPreferences interface.
     *
     * @return String
     */
    public String getMaxPeople() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(PEOPLE, "");
    }

    /**
     * Gets the maximumed allowed loudness from the SharedPreferences interface.
     *
     * @return String
     */
    public String getMaxLoudness() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(LOUDNESS, "");
    }

    /**
     * Gets the maximumed allowed humidity from the SharedPreferences interface.
     *
     * @return String
     */
    public String getMaxHumidity() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(HUMIDITY, "");
    }

    /**
     * Gets the maximumed allowed temperature from the SharedPreferences interface.
     *
     * @return String
     */
    public String getMaxTemperature() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString(TEMPERATURE, "");
    }

    /**
     * Gets the current connection status
     *
     * @return boolean
     */
    public boolean getConnectionStatus() {
        return isConnected;
    }

    /**
     * Sets the connection status.
     * Only used by tests to "manipulate" the connection. This since the display changes dependant on if it is connected or not.
     */
    public void setConnectionStatus(boolean status) {
        isConnected = status;
    }
}