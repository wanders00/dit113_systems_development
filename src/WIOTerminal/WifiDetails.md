<!-- This entire file could in the future be put under the installion part for the readme -->

# How to create a Wifi-Header file

#### 1. Create a file under "/src/WIOTerminal" called "WifiDetails.h"

<br>

#### 2. Implement this and specify the needed arguments

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

### How to find your IPv4
```
Open a terminal, run the command:
Windows: `ipconfig ` | MacOS: `/sbin/ifconfig`

Find and select:
"Wireless LAN adapter Wi-Fi: IPv4 Address"
```

#### 3. Done!

<br>

# Example setup on Windows

![Wifi-Details-Folder](/media/WifiDetailsFolder.png)

With the "WifiDetails.h" file containing this:

```c++
#define SSID "myNetwork"        // Example name
#define PASSWORD "123456789"    // Example password
#define my_IPv4 "192.168.1.1"   // Example IPv4
```

NOTE: DO NOT COPY THESE VALUES!
<br> You have to get your own values for each argument for your network.