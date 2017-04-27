# Ti.WifiManager

Titanium module for exposing Androids's WifiManager. The module can list the configured networks, can browse all access points. The module can connect to new AP. It supports WEP, WPA (PSK and Enterprise). The button method of WPS will supported.

Thanks to Jean-René Auger and Appwapp for sponsoring. <img src="https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQ-aerX3QEAscr_2jMrxcK_HEmQNQx2EFdtS1QGLeThOqHc61j3" width=100 />

<img src="https://www1-lw.xda-cdn.com/files/2012/08/Android-Wifi.png" width=200 />"

## Permissions

You need ACCESS_WIFI_STATE permission. Some functions need CHANGE_WIFI_STATE and
CHANGE_WIFI_MULTICAST_STATE.
```xml
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
<uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.pemissions.OVERRIDE_WIFI_CONFIG" />
```
The location permission is critical, you need a runtime permission!


## Usage

```javascript
var WM = require("ti.wifimanager");
```

## Receipts

### Scanning all access points
```javascript
var Wifi = require("ti.wifimanager");
Wifi.startWifiScan({
    complete : function(scanned) {
    console.log("runtime="+ scanned.runtime);
    if (scanned.scanResults) {
        scanned.scanResults.forEach(function(scanResult) {
        if (scanResult) {
            console.log("bssid=" + scanResult.getBSSID() + "   rssi=" + scanResult.getRSSI() + "   ssid=" + scanResult.getSSID());
        }
    }
});
```
Typical result:
```xml
[
{"timestamp":388418592430,"ssid":"Elysium","bssid":"38:10:d5:cf:bd:0e","wps":true,"rssi":"-51","security":"PSK","freq":"5240"}
{"timestamp":388418592418,"ssid":"Elysium","bssid":"38:10:d5:cf:bd:0d","wps":true,"rssi":"-47","security":"PSK","freq":"2437"}
{"timestamp":388418592444,"ssid":"o2-WLAN93","bssid":"1c:74:0d:27:92:f1","wps":true,"rssi":"-57","security":"PSK","freq":"2442"}
{"timestamp":388418592455,"ssid":"o2-WLAN93","bssid":"1c:74:0d:27:92:f2","wps":true,"rssi":"-63","security":"PSK","freq":"5180"}
{"timestamp":388418592477,"ssid":"o2-WLAN77","bssid":"88:03:55:10:b2:07","wps":true,"rssi":"-67","security":"PSK","freq":"2467"}
{"timestamp":388418592398,"ssid":"WILHELM.TEL-YWY52M7T","bssid":"34:31:c4:e4:2a:0e","wps":true,"rssi":"-43","security":"PSK","freq":"2422"}
{"timestamp":388418592466,"ssid":"WLAN-309178","bssid":"94:4a:0c:74:57:c3","wps":true,"rssi":"-69","security":"PSK","freq":"2412"}
{"timestamp":388418592510,"ssid":"WILHELM.TEL-J52R996K","bssid":"08:96:d7:02:2f:e4","wps":true,"rssi":"-89","security":"PSK","freq":"2462"}
{"timestamp":388418592499,"ssid":"HP-Print-6C-Photosmart 6520","bssid":"64:51:06:da:66:6c","wps":false,"rssi":"-88","security":"PSK","freq":"2437"}
{"timestamp":388418592489,"ssid":"WILHELM.TEL-267875C4","bssid":"5c:49:79:66:6a:0c","wps":true,"rssi":"-80","security":"PSK","freq":"2412"}
]
```
### Methods of scanResult
- [x] getBSSID()
- [x] getSSID()
- [x] getFrequency()
- [x] getSecurity()
- [x] getTimes()
- [x] gethasWPS()
- [x] getRSSI()
- [x] toString()

### Getting all configured APIs
```javascript
var Wifi = require("ti.wifimanager");
Wifi.getConfiguredNetworks().forEach(function(saved) {
    console.log(saved);
});
```
Typical result:
```xml
[
 {"priority":1,"SSID":"Ringelnetz","status":2,"id":11,"hidden":false,"BSSID":null}
 {"priority":1,"SSID":"IKEA WiFi","status":2,"id":72,"hidden":false,"BSSID":null}
 {"priority":1,"SSID":"Airport_Free_WiFi","status":2,"id":53,"hidden":false,"BSSID":null}
 {"priority":1,"SSID":"ROCKSTART","status":2,"id":37,"hidden":false,"BSSID":null}
 {"priority":1,"SSID":"asian beauty salon","status":2,"id":36,"hidden":false,"BSSID":null}
 {"priority":1,"SSID":"Tinkerforge","status":2,"id":71,"hidden":true,"BSSID":null}
 {"priority":1,"SSID":"FreeWifi","status":2,"id":23,"hidden":false,"BSSID":null}
 {"priority":1,"SSID":"TOURO","status":2,"id":54,"hidden":false,"BSSID":null}
 {"priority":1,"SSID":"akanoo_guest","status":2,"id":42,"hidden":false,"BSSID":null}
 {"priority":1,"SSID":"francesco","status":2,"id":55,"hidden":false,"BSSID":null}
 {"priority":1,"SSID":"GUEST@ROCKSTART","status":2,"id":52,"hidden":false,"BSSID":null}
```

### Configure a new AP (after scanning current networks)
```javascript
var Wifi = require("ti.wifimanager");
var networkId = Wifi.addNetwork({
    bssid : "f8:4f:57:37:f4:2f",
    security : "PSK", // optional, in most case automatic
    password : "sagichdirnicht",
    persist : true // is default
});
```

In case of enterprise WPA you need additional a `name`:
```javascript
var Wifi = require("ti.wifimanager");
var AP = Wifi.addNetwork({
    BSSID : "f8:4f:57:37:f4:2f",
    name : "DigitalTransformationOfficer"
    password : "sagichdirnicht"
});

```


### Connnect with new AP (or older)

```javascript
var Wifi = require("ti.wifimanager");
Wifi.enableNetwork({
    netId : netId,
    change : function() {
        // Hurra!
    });
});  
Wifi.addEventListener("change",function(event){
    if (event.online == true) {
    // Hurra!
    }
});
```
You can use for callback the property in method or the event listener.

### Connnect with new AP by WPS (PBC)

```javascript
var Wifi = require("ti.wifimanager");
Wifi.startWPS({
    setup : Wifi.WPS_PBC, 
    onconnected : function() {
    },
    onerror : function() {
},

})
```
### Connnect with new AP by WPS (Pin)

```javascript
var Wifi = require("ti.wifimanager");
Wifi.startWPS({
    setup : Wifi.WPS_PIN,
    onstarted : function(pin) {
       alert(pin);
    },
    onconnected : function() {
    },
    onerror : function() {
    },
})
```

### Constants


- [x] WM.ACTION_PICK_WIFI_NETWORK

_Activity Action: Pick a Wi-Fi network to connect to._ 

- [x] WM.ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE

_Activity Action: Show a system activity that allows the user to enable scans to be available even with Wi- turned off. _

### Config states
- [x] WM.CONFIG_STATUS_CURRENT

_This is the network we are currently connected to_

- [x] WM.CONFIG_STATUS_DISABLED

_supplicant will not attempt to use this network 

- [x] WM.CONFIG_STATUS_ENABLED

_supplicant will consider this network available for association 

### Status Wifi
- [x] WM.WIFI_STATE_ENABLED
- [x] WM.WIFI_STATE_DISABLED
- [x] WM.WIFI_STATE_ENABLING
- [x] WM.WIFI_STATE_DISABLING

### Removing networks
- [x] removeNetwork(networkId)
- [x] removeAllNetworks()


### Requesting details from Wifi

#### WM.getCurrentConnection()

Result:

- [x] SSID 
- [x] BSSID
- [x] IP-Address
- [x] LinkSpeed
- [x] MacAddress
- [x] RSSI
- [x] securityMode (OPEN, WEP, PSK, EAP)


#### WM.getScanResults()

Return the results of the latest access point scan

Result:

- [x] BSSID
- [x] SSID
- [x] securityMode
- [x] level
- [x] frequency
- [x] channelWidth
- [x] timestamp
- [x] status (WM.WifiConfiguration.STATUS_CURRENT,WM.WifiConfiguration.STATUS_ENABLED,WM.WifiConfiguration.STATUS_DISABLED)
- [x] isPasspointNetwork
- [x] is80211mcResponder
- [x] capabilities
- [x] venueName
- [x] operatorFriendlyName

### Other methods for inspecting the net

- [x] MW.saveConfiguration() // to persist new configuration
- [x] MW.is5GHzBandSupported()
- [x] MW.isDeviceToApRttSupported()
- [x] MW.isLocationServiceEnabled();
- [x] MW.isEnhancedPowerReportingSupported()
- [x] MW.isP2pSupported() 
- [x] MW.isPreferredNetworkOffloadSupported()
- [x] MW.isTdlsSupported()
- [x] MW.isScanAlwaysAvailable()

### Setting/updating

#### enableWifi()
#### disableWifi()
#### reconnect()
#### disableNetwork(BSSID);
#### enableNetwork(BSSID);
#### updateNetwork(wifiConfiguration)

```javascript
var wifiConfiguration = MW.createWifiConfiguration({
    BSSID : BSSID,
    secret : "WEPKEY_or_WPAsharedSecfret"
});
```

## WifiConfiguration
```javascript
var configuration = MW.createWifiConfiguration({
    BSSID : BSSID,
    secret : "WEPKEY_or_WPAsharedSecfret"
});
```
#### Testing of keys
```javascript
MW.isValidWEPKey();
MW.isValidBSSID();


```


## Handling

The WifiManager holds internally a list of network configurations (with "passwords").

With this method `WM.getConfiguredNetworks()` can you read all:

```javascript
var networks = WM.getConfiguredNetworks(); 
for (var i =0; i< networks.length;i++) {
console.log(networks[i].toJSON());
}
```
Result:
- [x] networkId
- [x] SSID
- [x] BSSID
- [x] priority
- [x] allowedProtocols
- [x] allowedKeyManagement
- [x] allowedAuthAlgorithms
- [x] allowedPairwiseCiphers
- [x] allowedGroupCiphers

You can extend this by adding a new WifiConfiguration creating a new one

```javascript
var myConf = WM.createWifiConfiguration({
    ssid : ssid
    sharedKey : "geheim", // for wpa
    wepKey : "2423A2E4" // for wep
});
```
 and adding:

```javascript
WM.addConfiguration(myConf);
```

## Browsing access point

Before you can add a new access point, you need SSID. For this you can get it by:

```javascript
// Return the results of the latest access point scan
var networks = WM.getScanResults(); 
for (var i =0; i< networks.length;i++) {
    console.log(networks[i]);
}
```
Result:

- [x] BSSID
- [x] SSID
- [x] securityMode
- [x] level
- [x] frequency
- [x] channelWidth
- [x] timestamp
- [x] isPasspointNetwork
- [x] is80211mcResponder
- [x] capabilities
- [x] venueName
- [x] operatorFriendlyName

## Connect with WPS (Wifi Protection Setup)

### WPS with Button (PBC)
```javascript
WM.startWPS({
    setup : WM.WPS_PBC, 
    onconnect : function() {
        console.log("Hurra!");
    },
    onerror : function(){}
});
```
### WPS with Pin 
```javascript
WM.startWPS({
    setup : WM.WPS_PIN,
    onadded : function(pin) {
        alert("Please add this pin " + pin +   " to your AccessPoint");
    }
    onconnect : function() {
        console.log("Hurra!");
    },
    onerror : function(){}
});
```
During connecting you can cancel the process with:
```javascript
WM.cancelWPS();
```
