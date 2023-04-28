/****************************************************************************
  Code based on "MQTT Exmple for SeeedStudio Wio Terminal".
  Author: Salman Faris
  Source: https://www.hackster.io/Salmanfarisvp/mqtt-on-wio-terminal-4ea8f8
*****************************************************************************/

// Libraries
#include <DHT.h>
#include <rpcWiFi.h>
#include <PubSubClient.h>

// Local header files
#include "MqttClient.hpp"
#include "Screen.hpp"
#include "WifiDetails.h"

const char *ssid = SSID;         // WiFi Name
const char *password = PASSWORD; // WiFi Password
const char *server = my_IPv4;    // MQTT Broker URL

const char *TOPIC_sub = "LocusImperium/APP/#";

WiFiClient wioClient;
PubSubClient client(wioClient);

/**
 * Sets up the wifi connection to the network entered in the WifiDetails.h file.
 * Will loop until it is successful.
 *
 * @return void
 */
void setupWifi() {
    delay(10);

    displayMessage("Connecting to wifi..");

    Serial.println();
    Serial.print("Connecting to ");
    Serial.println(ssid);
    WiFi.begin(ssid, password); // Connecting WiFi

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println("");
    Serial.println("WiFi connected");

    displayMessage("Connected!");
    delay(100);

    Serial.println("IP address: ");
    Serial.println(WiFi.localIP()); // Display Local IP Address
}

/**
 * Whenever a mqtt message is published to the broker that the client is subscribed to, callback() is called.
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
    displayMessage(msg_p);
}

/**
 * Loops until client is successfully connected again.
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
                client.subscribe(TOPIC_sub);
                displayMessage("Connected!");
                Serial.println("connected");
            }
            else {
                Serial.print("failed, rc=");
                Serial.print(client.state());
                Serial.println(" try again in 5 seconds");
                delay(5000);
            }
        }
        delay(500);
        flashScreen();
    }
}

/**
 * Initializes mqtt connection. Calls setup_wifi().
 *
 * @return void
 */
void mqttInit() {
    Serial.begin(115200);
    setupWifi();
    client.setServer(server, 1883); // Connect the MQTT Server
    client.setCallback(callback);
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
 * Check if client is still connected, if not, reconnect.
 * Then proceeds to run the mqtt client loop.
 *
 * @return boolean, true if client is connected, false if not.
 */
boolean mqttLoop() {
    if (!client.connected()) {
        reconnect();
        return false;
    }
    else {
        client.loop();
        return true;
    }
}