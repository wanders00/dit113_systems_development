<div align="center">

![Locus Imperium](/media/LocusImperium.gif)

### A room controlling device that reads room data attributes such as temperature, humidity, loudness, and estimates how many people are currently in the room.

</div>

# Table of Contents 

- [Links](#links)
- [Introduction](#introduction)
  - [Purpose & Benefits](#purpose--benefits)
  - [How the system works](#how-the-system-works)
  - [Tools we used](#tools-we-used)
- [Installation](#installation)
  - [Prerequisites](#prerequisites)
  - [Initial setup](#initial-setup)
  - [Installation steps](#installation-steps)
- [Developed by](#developed-by)

# Links

### [Wiki page 📚](https://git.chalmers.se/courses/dit113/2023/group-6/group-6/-/wikis/home)

### [Intro Video 📽️](https://drive.google.com/file/d/1Pbf4bzjwEkn8IahF9TAhGNku1HNMAWEZ/view?usp=sharing)

### [User Manual 📖](https://git.chalmers.se/courses/dit113/2023/group-6/group-6/-/wikis/user-manual)

# Introduction

## Purpose & Benefits

The purpose of this project is to create a device that reads room data attributes such as temperature, humidity, loudness, and the amount of people in a room. 

This information allows the user to study the air quality and ensure that they and their peers are in a healthy environment. For example:

- Keeping track of the room temperature and being able to regulate it whenever it exceeds the set limit guarantees everybody's comfort.
- Monitoring the level of humidity in the room and making sure it does not surpass the recommended relative humidity indoors could prevent undesirable conditions such as dry skin, dampness, mold formation, etc.
- The user can further prevent everyone around them from being in an unpleasant environment by keeping an eye on the loudness level. Any loud noise over a prolonged period of time may damage one's hearing.

All this information could be useful to determine a relation between the amount of people in the room and the air quality.

## How the system works

![Locus Imperium](/media/mqtt.png)

## Tools used

- [Android Studio](https://developer.android.com/studio)

- [Adobe Express](https://www.adobe.com/express/)

- [Mosquitto](https://mosquitto.org/)

- [Arduino CLI](https://arduino.github.io/arduino-cli/0.32/)

- [Seeed Studio Ecosystem & Grove Sensors](https://www.seeedstudio.com/)

<details>
<summary> Libraries used </summary>

- [Seeeduino SAMD Core (1.8.3)](https://www.seeedstudio.com/) by Seeed Studio
- [Grove Temperature And Humidity Sensor (2.0.1)](https://github.com/Seeed-Studio/Grove_Temperature_And_Humidity_Sensor) by Seeed Studio
- [Grove Ultrasonic Ranger (1.0.1)](https://github.com/Seeed-Studio/Seeed_Arduino_UltrasonicRanger) by Seeed Studio
- [PubSubClient (2.8.0)](https://github.com/knolleary/pubsubclient) by Nick O’Leary
- [FS (File System) (2.1.1)]() by Seeed Studio
- [rpcUnified (2.1.4)](https://github.com/Seeed-Studio/Seeed_Arduino_rpcUnified) by Seeed Studio
- [rpcWiFi (1.0.6)](https://github.com/Seeed-Studio/Seeed_Arduino_rpcWiFi) by Seeed Studio
- [SFUD (Serial Flash Universal Driver) (2.0.2)](https://github.com/Seeed-Studio/Seeed_Arduino_SFUD) by Seeed Studio
- [Mbed TLS (3.0.1)](https://github.com/Seeed-Studio/Seeed_Arduino_mbedtls) by Seeed Studio

</details>

# Installation

## Prerequisites

### Hardware

 - [WIO Terminal](https://wiki.seeedstudio.com/Wio-Terminal-Getting-Started/)
 - [Wio Terminal Chassis Battery](https://wiki.seeedstudio.com/Wio-Terminal-Battery-Chassis/)
 - [Grove Ultrasonic Sensor](https://wiki.seeedstudio.com/Grove-Ultrasonic_Ranger/) x 2
 - [Grove Temperature and Humidity Sensor](https://wiki.seeedstudio.com/Grove-TemperatureAndHumidity_Sensor/)
 - [Grove Buzzer](https://wiki.seeedstudio.com/Grove-Buzzer/)

 <details>
 <summary> Hardware Architecture </summary>
 
![Hardware Architecture](media/IMG_4191.png)
 </details>

### Software

  - [Arduino CLI](https://arduino.github.io/arduino-cli/0.32/) properly installed (in System Environment Variables: Path for Windows or $PATH for linux)

  - If you desire to host your own broker, a WiFi connection that supports MQTT is required. Make sure to connect the WIO, App and broker device to the same network.

## Initial setup

> Before starting, ensure you have the following [prerequisites](#prerequisites).

## Installation steps

Installing **Locus Imperium** on the **WIO Terminal** and your **Android Device**

1. Open your terminal and clone the repository 
   ```
   git clone https://git.chalmers.se/courses/dit113/2023/group-6/group-6.git
   ```
2. Go to the installation repository
    ```
    cd group-6
    ```
3. Connect the **WIO Terminal** and your **Android Device** to your computer with USB or USB Type C
4. Run the install script adequate for your operative system and follow the instructions
**Note:** the linux script *should* work on Mac as well

    In Linux:
    ```
    ./linux_install.sh
    ```

    In Windows:
    ```
    .\windows_install.bat
    ```
5. Follow the steps on the install script.
6. Done!

## (Optional) Installing **Mosquitto** on your dedicated broker machine

1. Download [Mosquitto](https://mosquitto.org/download/) and follow the installation.

2. Go to the mosquitto folder and open `mosquitto.config` file in your computer, below "General configuration" add:

    ```
    listener 1883 0.0.0.0
    allow_anonymous true
    ```
3. Open a terminal in the folder and enter the following script:

    In Windows and Linux:
    ```
    mosquitto -c mosquitto.conf -v
    ```
    In MacOS:
    ```
    brew services start mosquitto
    ```

4. Done! The broker is now online on the network your device is connected to!

<details>
<summary> How to find your IPv4: </summary>

1. Open a terminal and run:

    In Windows
    ```
    ipconfig
    ```
    In Linux
    ```
    ip addr show
    ```
    In MacOS:
    ```
    cat /sbin/ifconfig
    ```

2. Find and select: 
<br> `Wireless LAN adapter Wi-Fi: IPv4 Address`

</details>

**Note:** On Windows: If you receive "Error: Only one usage of each socket address", terminate the already running mosquitto process
  - Open Task Manager (ctrl+shift+esc) and under processes kill `mosquitto.exe`

# Developed by

### William Andersson

- Implemented the mqtt connection and how each part of the different systems should communicate with each other. Also was a part of developing the GUI for the WIO and the automated build script. Furthermore, worked on alerts, bugfixes, and general things.

### Joshua Chiu Falck

- Created the the android application functionality with the initial design. Functionality such as save & load settings, alerts, connecting with a different IP, displaying data from the broker as well as manually updating the people counter. Also assisted in the mqtt connection through the two systems and contributed to the wiki
 

### Carlos Campos Herrera

- Mainly worked on the WIO Terminal and the pipeline. Contributed to the readme, the wiki and the android app.

### Marcelo Santibáñez

- Firstly worked on MQTT connection. Implemented functionalities on the WIO terminal, screens on the Android app and also GUI and unit tests for the Java code of the app. Edited all the videos of the project.

### Andrii Demchenko 

- Developed the people movement detection algorithm, loudness measurement, and redesigned the mobile app. Also worked on test coverage reporting and contributed to the documentation.

### Vasilena Karaivanova 

- Contributed to the functionality of some sensors. Worked on the WIO Terminal for the most part, in terms of GUI and alerts. Additionally, worked on documentation & wiki-page.