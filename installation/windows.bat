@echo off

:: ---- WIO ----

:: Installing core
arduino-cli core install Seeeduino:samd@1.8.3

:: Installing libraries
arduino-cli lib install "Seeed Arduino rpcWiFi"@1.0.6 --no-deps
arduino-cli lib install "Seeed Arduino rpcUnified"@2.1.4
arduino-cli lib install "PubSubClient"@2.8.0
arduino-cli lib install "Grove Ultrasonic Ranger"@1.0.1
arduino-cli lib install "Grove Temperature And Humidity Sensor"@2.0.1
arduino-cli lib install "Seeed Arduino SFUD"@2.0.2
arduino-cli lib install "Seeed Arduino FS"@2.1.1
arduino-cli lib install "Seeed_Arduino_mbedtls"@3.0.1

:: Picking WiFi details & mqtt broker address
echo:
set /p wifiName=Enter WiFi name: 

echo:
set /p wifiPassword=Enter WiFi password:

echo:
set /p ipv4=Enter your desired broker ipv4: 

echo #define SSID "%wifiName%" > ../src/WIOTerminal/WifiDetails.h
echo #define PASSWORD "%wifiPassword%" >> ../src/WIOTerminal/WifiDetails.h
echo #define brokerAddress "%ipv4%" >> ../src/WIOTerminal/WifiDetails.h

:: Picking port
echo:
arduino-cli board list
set /p port=Please select the port to use: 

:: Compiling and uploading
arduino-cli compile -b Seeeduino:samd:seeed_wio_terminal -p %port% ../src/WIOTerminal --upload

:: Cleaning cache
arduino-cli cache clean

:: Closing
set /p close=Press any key to close... 