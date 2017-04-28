package ti.wifimanager;

import java.util.regex.Pattern;

/**
 * WEP - Wired Equivalent Privacy (WEP) is a security algorithm. Utility class
 * to check if a WEP key is valid or not. WEP key is represented in HEX and its
 * length depends on the bits used for encryption. More the bits, higher is the
 * security and long is key.
 */
class WEPKey {
	private static final Pattern HEX_DIGITS = Pattern.compile("[0-9A-Fa-f]+");

	/**
	 * Check if wepKey is a valid hexadecimal string.
	 * 
	 * @param wepKey
	 *            the input to be checked
	 * @return true if the input string is indeed hex or empty. False if the
	 *         input string is non-hex or null.
	 */
	public static boolean isValid(CharSequence wepKey) {

		// null check
		if (wepKey == null) {
			return false;
		}

		int length = wepKey.length();
		// WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
		return (length == 10 || length == 26 || length == 58)
				&& HEX_DIGITS.matcher(wepKey).matches();
	}

	private static boolean getHexKey(String s) {
		if (s == null) {
			return false;
		}

		int len = s.length();
		if (len != 10 && len != 26 && len != 58) {
			return false;
		}

		for (int i = 0; i < len; ++i) {
			char c = s.charAt(i);
			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f')
					|| (c >= 'A' && c <= 'F')) {
				continue;
			}
			return false;
		}
		return true;
	}

}
