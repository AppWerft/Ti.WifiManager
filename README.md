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
```

## Usage

```javascript
var WM = require("ti.wifimanager");
```


### Constants


- [x] WM.ACTION_PICK_WIFI_NETWORK

_Activity Action: Pick a Wi-Fi network to connect to._ 

- [x] WM.ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE

_Activity Action: Show a system activity that allows the user to enable scans to be available even with Wi- turned off. _

- [x] WM.WifiConfiguration.STATUS_CURRENT

_This is the network we are currently connected to_

- [x] WM.WifiConfigurationStatus.STATUS_DISABLED

_supplicant will not attempt to use this network 

- [x] WM.WifiConfigurationStatus.STATUS_ENABLED

_supplicant will consider this network available for association 


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

- [x] MW.is5GHzBandSupported()
- [x] MW.isDeviceToApRttSupported()
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
MW.WifiConfiguration.isValidWEPKey();
MW.WifiConfiguration.isValidBSSID();


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
```javascript
WM.Wps.start({
    setup : WM.Wps.PBC, // only this at this moment,
    onconnect : function() {},
    onerror : function(){}
});
```
During connecting you can cancel the process with:
```javascript
WM.Wps.cancel();
```
