package ti.wifimanager;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class WifiChangeEventListener extends BroadcastReceiver {
	public static WifimanagerModule proxy;
	public static KrollFunction onChange;

	@Override
	public void onReceive(Context ctx, Intent intent) {
		ConnectivityManager conMan = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMan.getActiveNetworkInfo();
		KrollDict kd = new KrollDict();
		if (netInfo != null
				&& netInfo.getType() == ConnectivityManager.TYPE_WIFI)
			kd.put("online", true);
		else
			kd.put("online", false);

		if (proxy.hasListeners(WifimanagerModule.CHANGE)) {
			proxy.fireEvent(WifimanagerModule.CHANGE, kd);
		}
		if (onChange != null)
			onChange.call(proxy.getKrollObject(), kd);
	}
}