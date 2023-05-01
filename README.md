
<div align="center">

![Locus Imperium](/media/LocusImperium.gif)

### A room controlling device that reads room data attributes such as temperature, humidity and estimates how many people are currently in the room.

<!-- TODO: improve text here ^ -->

</div>

<br>

# Table of Contents 

- [Table of Contents](#table-of-contents)
- [Links](#links)
    - [Wiki page 📖](#wiki-page-)
    - [Intro Video 📽️](#intro-video-️)
- [Introduction](#introduction)
  - [Why the project is useful](#why-the-project-is-useful)
  - [How we will make it](#how-we-will-make-it)
  - [Tools we used](#tools-we-used)
- [Prerequisites](#prerequisites)
    - [Dependencies](#dependencies)
- [Installation](#installation)
  - [Initial setup](#initial-setup)
  - [How to create a Wifi-Header file](#how-to-create-a-wifi-header-file)
    - [How to find your IPv4](#how-to-find-your-ipv4)
    - [Example setup on Windows](#example-setup-on-windows)
- [Developed by](#developed-by)



# Links

### [Wiki page 📖](https://git.chalmers.se/courses/dit113/2023/group-6/group-6/-/wikis/home)

<br>

### [Intro Video 📽️](https://drive.google.com/file/d/1Pbf4bzjwEkn8IahF9TAhGNku1HNMAWEZ/view?usp=sharing)

<br>

# Introduction

## Why the project is useful

- By knowing the room temperature you are able to tell if the temperature is too high or not.

- By knowing how many people are in the room you are able to tell if the safety hazard is broken.

- By knowing the humidity you are able to determine if actions neccessary. Example high humidity leads to mold formation.

- This information could be useful to determine a relation between the ammount of people in the room and the air quality.

## How we will make it

![Locus Imperium](/media/mqtt.png)

## Tools we used

- Adobe Express

- Wio Terminal

- [Mosquitto](https://mosquitto.org/)

- Grove sensors & their respective libraries
  - Buzzer
  - Loudness
  - Multi Color Flash
  - Temperature & Humidity
  - Ultrasonic Ranger

# Prerequisites
 - Arduino shell | [Download](https://www.arduino.cc/en/software)
 - Optionally, Arduino IDE instead of the shell | [Download](https://www.arduino.cc/en/software)
 - Valid C++ compiler
 - Java JDK | [Download](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html)
  ### Dependencies
  <details>
  <summary> Expand </summary>
  Please Install the following libraries using the Arduino shell library manager:

  - Adafruit FONA Library
  - Adafruit MQTT Library
  - Adafruit SleepyDog Library
  - Grove Temperature And Humidity Sensor
- Grove Ultrasonic Ranger         
- PubSubClient       
- Seeed Arduino FS     
- Seeed Arduino rpcUnified       
- Seeed Arduino rpcWiFi       
- Seeed Arduino SFUD         
- Seeed_Arduino_mbedtls        
- TFT         
- WiFi101

</details>

# Installation
## Initial setup
> Before starting, ensure you have following [prerequisites](#prerequisites)
1. Clone the repository 
   ```
   git clone https://git.chalmers.se/courses/dit113/2023/group-6/group-6.git
   ```
2. Open the project in the IDE of your choice
3. Setup the Wifi-Header file (see [How to create a Wifi-Header file](#how-to-create-a-wifi-header-file))
4. Connect the Wio Terminal to your computer
5. Upload the code to the Wio Terminal using the IDE/Shell
## How to create a Wifi-Header file

**1. Create a file under [/src/WIOTerminal](/src/WIOTerminal) called [WifiDetails.h](/src/WIOTerminal/WifiDetails.h)**

<br>

**2. Implement this and specify the needed arguments**

```c++
#define SSID "<WiFi name>" 
#define PASSWORD "<the WiFi password>" 
#define my_IPv4 "<ipv4>"
```

| Argument | Clarification |
|---|---|
|SSID | The name of the network. |
|PASSWORD | The network's password. |
|my_IPv4 | The IPv4 of the device. |
<br>

### How to find your IPv4
<details>
<summary> Expand </summary>

- Open a terminal, run the command: Windows: `ipconfig ` | MacOS: `/sbin/ifconfig` | Linux: *you know how to do it already*.

or

- Find and select:
"Wireless LAN adapter Wi-Fi: IPv4 Address"


</details>
<br>

 **3. Done!**

<br>

### Example setup on Windows
<details>
<summary> Expand </summary>

![Wifi-Details-Folder](/media/WifiDetailsFolder.png)

With the "WifiDetails.h" file containing this:

```c++
#define SSID "myNetwork"        // Example name
#define PASSWORD "123456789"    // Example password
#define my_IPv4 "192.168.1.1"   // Example IPv4
```

> NOTE: DO NOT COPY THESE VALUES!
<br> You have to get your own values for each argument for your network.
</details>

# Developed by

- William Andersson :flag_se:
- Joshua Chiu Falck :flag_se:
- Carlos Campos Herrera :flag_es:
- Marcelo Santibáñez :flag_cl:
- Andrii Demchenko :flag_ua:
- Vasilena Karaivanova :flag_bg:
