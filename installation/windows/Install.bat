@echo off

:: ---- WIO ----

:: Config init
arduino-cli config init
arduino-cli config set library.enable_unsafe_install true

:: Installing core
arduino-cli core install Seeeduino:samd@1.8.3

:: Installing libraries
arduino-cli lib install "Seeed Arduino rpcWiFi"
arduino-cli lib install "PubSubClient"
arduino-cli lib install "Grove Ultrasonic Ranger"
arduino-cli lib install "Grove Temperature And Humidity Sensor"
arduino-cli lib install --git-url https://github.com/Seeed-Studio/Seeed_Arduino_rpcWiFiManager

:: Dependecies of "Seeed Arduino rpcWiFi" so will be installed by that.
:: arduino-cli lib install "Seeed Arduino SFUD"
:: arduino-cli lib install "Seeed Arduino FS"
:: arduino-cli lib install "Seeed_Arduino_mbedtls"

:: Picking WiFi details & mqtt broker address
echo:
set /p wifiName=Enter WiFi name: 

echo:
set /p wifiPassword=Enter WiFi password:

echo:
set /p ipv4=Enter your desired broker ipv4: 

echo #define SSID "%wifiName%" > ../../src/WIOTerminal/WifiDetails.h
echo #define PASSWORD "%wifiPassword%" >> ../../src/WIOTerminal/WifiDetails.h
echo #define brokerAddress "%ipv4%" >> ../../src/WIOTerminal/WifiDetails.h

:: Picking port
echo:
arduino-cli board list
set /p port=Please select the port to use: 

:: Compiling and uploading
arduino-cli compile -b Seeeduino:samd:seeed_wio_terminal -p %port% ../../src/WIOTerminal --upload

:: Cleaning cache
arduino-cli cache clean

:: Closing
set /p close=Press any key to close... 