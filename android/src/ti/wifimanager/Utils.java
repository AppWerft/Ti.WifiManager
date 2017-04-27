package ti.wifimanager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import android.content.pm.PackageManager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

public class Utils {
	private static final String LCAT = WifimanagerModule.LCAT;

	public static boolean supportsWPS(ScanResult res) {
		return res.capabilities.contains("WPS");
	}

	public static boolean hasPermission(String p) {
		return (TiApplication.getInstance().getApplicationContext()
				.checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) ? false
				: true;
	}

	public static String getScanResultSecurity(ScanResult res) {
		final String[] securityModes = { WifimanagerModule.WEP,
				WifimanagerModule.PSK, WifimanagerModule.EAP };
		for (int i = securityModes.length - 1; i >= 0; i--) {
			if (res.capabilities.contains(securityModes[i])) {
				return securityModes[i];
			}
		}
		return WifimanagerModule.OPEN;
	}

	public static KrollDict ScanResultToKrollDict(ScanResult res) {
		KrollDict ap = new KrollDict();
		ap.put("BSSID", res.BSSID);
		ap.put("SSID", res.SSID.replace("\\\"", ""));
		ap.put("securityMode", getScanResultSecurity(res));
		ap.put("level", WifiManager.calculateSignalLevel(res.level, 0));
		ap.put("timestamp", res.timestamp);
		ap.put("is80211mcResponder", res.is80211mcResponder());
		ap.put("isPasspointNetwork", res.isPasspointNetwork());
		ap.put("capabilities", res.capabilities);
		ap.put("venueName", res.venueName.toString());
		ap.put("operatorFriendlyName", res.operatorFriendlyName.toString());
		return ap;
	}

	public static boolean isValidMac(String mac) {
		final String REGEX = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
		Log.d(LCAT, "test if " + mac + " is valid");
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(mac);
		return m.find();
	}

	public static boolean isValidSecurity(String s) {
		return (s.equals("PSK") || s.equals("WPA") || s.equals("Open") || s
				.equals("WEP")) ? true : false;
	}

	public static KrollDict isLocationServiceEnabled() {
		LocationManager lm = (LocationManager) TiApplication.getInstance()
				.getBaseContext().getSystemService(Context.LOCATION_SERVICE);
		boolean gps_enabled = false;
		boolean network_enabled = false;

		try {
			gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}
		try {
			network_enabled = lm
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
		}
		KrollDict res = new KrollDict();
		res.put("gps", gps_enabled);
		res.put("network", network_enabled);
		return res;
	}
}
