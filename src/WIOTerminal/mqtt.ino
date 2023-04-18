// THIS ENTIRE FILE NEED TO BE REWAMPED TO FOLLOW THE STRUCTURE OF THE CODE, WAS STARTED UPON BEFORE ANY MEANINGFUL PROGRESS HAD BEEN MADE.

#include <DHT.h>
#include <rpcWiFi.h>
#include "TFT_eSPI.h"
#include <PubSubClient.h>
#include "WifiDetails.h"

// int value; //TODO: REMOVE THIS

const char *ssid = SSID;         // WiFi Name
const char *password = PASSWORD; // WiFi Password
const char *server = my_IPv4;    // MQTT Broker URL

const char *TOPIC_sub = "LocusImperium/APP/#";
const char *TOPIC_pub_connection = "LocusImperium/WIO/loudnessValue";

TFT_eSPI tft;

WiFiClient wioClient;
PubSubClient client(wioClient);

void setup_wifi()
{

    delay(10);

    tft.setTextSize(2);
    tft.setCursor((320 - tft.textWidth("Connecting to Wi-Fi..")) / 2, 120);
    tft.print("Connecting to Wi-Fi..");

    Serial.println();
    Serial.print("Connecting to ");
    Serial.println(ssid);
    WiFi.begin(ssid, password); // Connecting WiFi

    while (WiFi.status() != WL_CONNECTED)
    {
        delay(500);
        Serial.print(".");
    }

    Serial.println("");
    Serial.println("WiFi connected");

    tft.fillScreen(TFT_BLACK);
    tft.setCursor((320 - tft.textWidth("Connected!")) / 2, 120);
    tft.print("Connected!");

    Serial.println("IP address: ");
    Serial.println(WiFi.localIP()); // Display Local IP Address
}

void callback(char *topic, byte *payload, unsigned int length)
{
    // tft.fillScreen(TFT_BLACK);
    Serial.print("Message arrived [");
    Serial.print(topic);
    Serial.print("] ");
    char buff_p[length];
    for (int i = 0; i < length; i++)
    {
        Serial.print((char)payload[i]);
        buff_p[i] = (char)payload[i];
    }
    Serial.println();
    buff_p[length] = '\0';
    String msg_p = String(buff_p);
    tft.fillScreen(TFT_BLACK);
    tft.setCursor((320 - tft.textWidth("MQTT Message")) / 2, 90);
    tft.print("MQTT Message: ");
    tft.setCursor((320 - tft.textWidth(msg_p)) / 2, 120);
    tft.print(msg_p); // Print receved payload
}

void reconnect()
{
    // Loop until we're reconnected
    while (!client.connected())
    {
        Serial.print("Attempting MQTT connection...");
        // Create a random client ID
        String clientId = "WioTerminal-";
        // Attempt to connect
        if (client.connect(clientId.c_str()))
        {
            Serial.println("connected");
            // Once connected, publish an announcement...
            client.publish(TOPIC_pub_connection, "hello world");
            // ... and resubscribe
            client.subscribe(TOPIC_sub);
        }
        else
        {
            Serial.print("failed, rc=");
            Serial.print(client.state());
            Serial.println(" try again in 5 seconds");
            // Wait 5 seconds before retrying
            delay(5000);
        }
    }
}

/*
void setup()
{
    value = 0;
    tft.begin();
    tft.fillScreen(TFT_BLACK);
    tft.setRotation(3);

    Serial.println();
    Serial.begin(115200);
    setup_wifi();
    client.setServer(server, 1883); // Connect the MQTT Server
    client.setCallback(callback);
}

void loop()
{

    if (!client.connected())
    {
        reconnect();
    }
    client.loop();

    tft.drawString(String(value), 50, 50);

    std::string s = std::to_string(value);
    char const *pchar = s.c_str(); 

    client.publish(TOPIC_pub_connection, pchar);
    value++;
    delay(50);

}

*/