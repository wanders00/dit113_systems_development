
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
  - [Setting up WIO Terminal](#setting-up-wio-terminal)
  - [Setting up Android App](#setting-up-android-app)
- [Developed by](#developed-by)

# Links

### [Wiki page 📖](https://git.chalmers.se/courses/dit113/2023/group-6/group-6/-/wikis/home)

### [Intro Video 📽️](https://drive.google.com/file/d/1Pbf4bzjwEkn8IahF9TAhGNku1HNMAWEZ/view?usp=sharing)

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
 
![](media/IMG_4191.png)
 </details>

### Software
 - Valid C++ compiler
 - Java JDK | [Download](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html)
  - (Optional) Arduino shell [Download](https://www.arduino.cc/en/software) | OR | Arduino IDE [Download](https://www.arduino.cc/en/software)

## Initial setup
> Before starting, ensure you have the following [prerequisites](#prerequisites).

## Setting up WIO Terminal
1. Clone the repository 
   ```
   git clone https://git.chalmers.se/courses/dit113/2023/group-6/group-6.git
   ```
2. Connect the Wio Terminal to your computer
3. Open the installation folder and select the folder for your operating system.
4. Execute the  `run` file.
5. Enter values for your WiFi and broker address. See [how to find your IPv4.](#how-to-find-your-ipv4)
6. Done!

### How to find your IPv4
<details>
<summary> Expand </summary>

- Open a terminal, run the command: Windows: `ipconfig` | MacOS: `/sbin/ifconfig` | Linux: *you know how to do it already*.

- Find and select:
"Wireless LAN adapter Wi-Fi: IPv4 Address"

</details>

## Setting up Android App

<!-- TODO -->

# Developed by

- William Andersson :flag_se:
- Joshua Chiu Falck :flag_se:
- Carlos Campos Herrera :flag_es:
- Marcelo Santibáñez :flag_cl:
- Andrii Demchenko :flag_ua:
- Vasilena Karaivanova :flag_bg:
