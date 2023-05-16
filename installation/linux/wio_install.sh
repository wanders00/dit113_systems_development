#!/bin/bash

#Check that arduino-cli is accessible for the script
if ! command -v arduino-cli &> /dev/null
then
    echo "arduino-cli is not installed or has not been added to path"
    exit 1
fi

readonly wifi_details_path="../../src/WIOTerminal/WifiDetails.h"


#Install libraries
arduino-cli lib install "Seeed Arduino rpcWiFi"@1.0.6 --no-deps
arduino-cli lib install "Seeed Arduino rpcUnified"@2.1.4
arduino-cli lib install "PubSubClient"@2.8.0
arduino-cli lib install "Grove Ultrasonic Ranger"@1.0.1
arduino-cli lib install "Grove Temperature And Humidity Sensor"@2.0.1
arduino-cli lib install "Seeed Arduino SFUD"@2.0.2
arduino-cli lib install "Seeed Arduino FS"@2.1.1
arduino-cli lib install "Seeed_Arduino_mbedtls"@3.0.1

#Function to ask the user and read it's answer
read_param () {
    printf "$1"

    while :
    do
        read -r answer   

        if [ -z "$answer" ] ;then
            printf "Please provide a value: "

        else
            user_answer="$answer"
            break
        fi
    done
}

#Pick the Wifi details and mqtt broker address
read_param "Enter WIFI name: "
echo "#define SSID $user_answer" > $wifi_details_path

read_param "Enter WIFI password: "
echo "#define PASSWORD $user_answer" >> $wifi_details_path

read_param "Enter your desired broker ipv4: "
echo "#define brokerAddress $user_answer" >> $wifi_details_path


#Pick port
read_param "Please select the port to use: "

arduino-cli cache clean

#Compile and upload to the WIO
arduino-cli compile -b Seeeduino:samd:seeed_wio_terminal -p "$user_answer" ../../src/WIOTerminal --upload