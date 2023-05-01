/****************************************************************************
  Code based on "MQTT Example for SeeedStudio Wio Terminal".
  Code based on "MQTT Example for SeeedStudio Wio Terminal".
  Author: Salman Faris
  Source: https://www.hackster.io/Salmanfarisvp/mqtt-on-wio-terminal-4ea8f8
*****************************************************************************/

// Libraries
#include <DHT.h>
#include <PubSubClient.h>
#include <rpcWiFi.h>

// Local header files
#include "Buzzer.hpp"
#include "MqttClient.hpp"
#include "Screen.hpp"
#include "Util.hpp"
#include "WifiDetails.h"
#include "Settings.hpp"

// Wi-Fi details
const char *ssid = SSID;          // WiFi Name
const char *password = PASSWORD;  // WiFi Password

// MQTT details
const char *BROKER_ADDRESS = my_IPv4;                    // Broker URL
const char *SUBSCRIPTION_TOPIC = "LocusImperium/APP/#";  // Topic to subscribe to
const String CLIENT_ID = "WioTerminal";                  // Client ID used on broker

const char *MAX_PEOPLE_TOPIC = "LocusImperium/APP/maxPeopleCount";

// To not allow attempts to often
uint32_t whenLastAttemptedReconnect;
const uint32_t attemptFrequency = 5000;  // Frequency in milliseconds

const char *SUBSCRIPTION_TOPIC = "LocusImperium/APP/#";
const char *MAX_PEOPLE_TOPIC = "LocusImperium/WIO/maxPeopleCount";

WiFiClient wioClient;
PubSubClient client(wioClient);

/**
 * Initializes mqtt connection. Calls setup_wifi().
 *
 * @return void
 */
void mqttInit() {
    whenLastAttemptedReconnect = 0;
    Serial.begin(115200);
    setupWifi();
    client.setServer(BROKER_ADDRESS, 1883);  // Connect the MQTT Server
    client.setCallback(callback);
    setupMqtt();
    haveAlerted = false;
    wifiConnection = true;
    mqttConnection = true;
}

/**
 * Sets up the wifi connection to the network entered in the WifiDetails.h file.
 * Will loop until it is successful.
 *
 * @return void
 */
void setupWifi() {
    displayMessage("Connecting to wifi..");

    WiFi.begin(ssid, password);  // Connecting WiFi

    while (WiFi.status() != WL_CONNECTED) {
        // To not attempt to often.
        timeoutTimer(1000);
    }

    displayMessage("Connected!");
    timeoutTimer(100);
}

/**
 * Sets up the mqtt connection to the broker specified in the WifiDetails.h file (ipv4).
 * Will loop until it is successful.
 *
 * @return void
 */
void setupMqtt() {
    displayMessage("Connecting to MQTT..");

    while (!client.connected()) {
        // Attempt to connect
        if (client.connect(CLIENT_ID.c_str())) {
            client.subscribe(SUBSCRIPTION_TOPIC);
        }
        // To not attempt to often.
        timeoutTimer(1000);
    }

    displayMessage("Connected!");
    timeoutTimer(100);
}

/**
 * Check if client is still connected to the broker, if not, calls reconnect().
 * Otherwise, proceeds to run the mqtt client loop.
 *
 * @return boolean, true if client is connected, false if not.
 */
boolean mqttLoop() {
    if (!client.connected()) {
        // Reconnect if not connected.
        reconnect();
        return false;

    } else {
        // Connection works.
        wifiConnection = true;
        mqttConnection = true;

        // Too play alert if it loses connection.
        haveAlerted = false;

        // Mqtt loop
        client.loop();
        return true;
    }
}

/**
 * Whenever a mqtt message is published to the broker that the client is subscribed to, callback() is called automatically.
 * @note So far will display the message on screen, need to be revisited in the future.
 *
 * @param topic the mqtt topic of the message received.
 * @param payload the payload of the message received.
 * @param length the length of the message.
 * @return void
 */
void callback(char *topic, byte *payload, unsigned int length) {
    Serial.print("Message arrived [");
    Serial.print(topic);
    Serial.print("] ");
    char buff_p[length];

    for (int i = 0; i < length; i++) {
        Serial.print((char)payload[i]);
        buff_p[i] = (char)payload[i];
    }
    Serial.println();
    buff_p[length] = '\0';
    String msg_p = String(buff_p);

    if (topic == MAX_PEOPLE_TOPIC) {
        setMaxPeople(msg_p.toInt());
    }
    else {
        displayMessage("Message: " + msg_p);
    }
}

/**
 * Attemps to:
 * First reconnect to the Wi-Fi if not connected.
 * Secondly reconnect to the broker if not connected.
 *
 * @return void
 */
void reconnect() {
    // If WiFi is not connected, reconnect to it first instead.
    if (WiFi.status() != WL_CONNECTED) {
        displayMessage("Reconnecting to Wi-Fi");
        WiFi.reconnect();
        delay(5000);
    }
    else {
        // Loop until we're reconnected to the broker
        while (!client.connected()) {
            displayMessage("Connecting to mqtt broker..");
            Serial.print("Attempting MQTT connection..");
            delay(100);
            // Create a client ID for the broker
            String clientId = "WioTerminal-";
            // Attempt to connect
            if (client.connect(clientId.c_str())) {
                client.subscribe(SUBSCRIPTION_TOPIC);
                displayMessage("Connected!");
            }
        }
    }
}

/**
 * Publishes a payload to the predefined mqtt broker.
 *
 * @param topic The mqtt publish address.
 * @param message The payload to publish.
 * @return void
 */
void publishMessage(const char *topic, const char *message) {
    client.publish(topic, message);
}

/**
 * Publishes a payload to the predefined mqtt broker.
 * Converts the string message to const char* before publishing.
 *
 * @param topic The mqtt publish address.
 * @param message The payload to publish.
 * @return void
 */
void publishMessage(const char *topic, String message) {
    char const *pchar = message.c_str();
    publishMessage(topic, pchar);
}

/**
 * Plays an alert that the connection has been lost, either to the Wi-Fi or the broker.
 * Calls displayAlert() and buzzerAlert().
 *
 * @return void
 */
void playConnectionLostAlert() {
    haveAlerted = true;
    String alertMessage = "Lost connection to ";
    if (WiFi.status() != WL_CONNECTED) {
        displayAlert(alertMessage + "WiFi.");
    } else {
        displayAlert(alertMessage + "broker.");
    }
    buzzerAlert();
}

/**
 * State of the WiFi connection.
 *
 * @return bool, true if wifi is connected, else false.
 */
bool getWifiConnection() {
    return wifiConnection;
}

/**
 * State of the mqtt broker connection.
 *
 * @return bool, true if mqtt is connected, else false.
 */
bool getMqttConnection() {
    return mqttConnection;
}
