package ti.wifimanager;

public class StringUtils {

	public static final String EMPTYSTRING = "";
	public static final String WPA = "WPA";
	public static final String WPA2 = "WPA2";
	public static final String WEP = "WEP";
	public static final String OPEN = "OPEN";
	public static final String WPS = "WPS";
	public static final CharSequence ESS = "ESS";

	public static String addQuotes(String s) {
		return "\"" + s + "\"";
	}

	public static String removeQuotes(String ssid) {
		if (ssid == null)
			return EMPTYSTRING;
		else if (ssid.endsWith("\"") && ssid.startsWith("\"")) {
			try {
				ssid = ssid.substring(1, ssid.length() - 1);
			} catch (IndexOutOfBoundsException e) {
				return EMPTYSTRING;
			}
		}
		return ssid;
	}

	public static String trimStringEnds(String s) {
		try {
			s = s.substring(1, s.length() - 1);
		} catch (IndexOutOfBoundsException e) {
			return EMPTYSTRING;
		}
		return s;
	}

	public static String getLongCapabilitiesString(String capabilities) {
		if (capabilities.length() == 0)
			return OPEN;
		else
			return capabilities;
	}

	public static String getCapabilitiesString(String capabilities) {
		if (capabilities.length() == 0)
			return OPEN;
		else if (capabilities.contains(WEP))
			return WEP;
		else if (capabilities.contains(WPA2))
			return WPA2;
		else if (capabilities.contains(WPA))
			return WPA;
		else if (capabilities.contains(WPS))
			return WPS;
		else
			return OPEN;
	}

}