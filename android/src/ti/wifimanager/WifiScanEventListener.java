package ti.wifimanager;

import java.util.ArrayList;
import java.util.List;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollObject;
import org.appcelerator.titanium.TiApplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

public class WifiScanEventListener {
	// Logging
	private static final String LCAT = WifimanagerModule.LCAT;

	// Instance variables
	private Context ctx;
	private WifiManager wifiManager;
	private KrollFunction onScanComplete;
	private KrollObject kroll;
	private long startTime = System.currentTimeMillis();

	private BroadcastReceiver wifiEventReceiver;

	public void setmWifiEventReceiver(BroadcastReceiver wifiEventReceiver) {
		this.wifiEventReceiver = wifiEventReceiver;
	}

	public BroadcastReceiver getmWifiEventReceiver() {
		return wifiEventReceiver;
	}

	// Constructor
	public WifiScanEventListener(Context ctx,
			final KrollFunction onScanComplete, final KrollObject kroll) {
		this.ctx = ctx;
		this.onScanComplete = onScanComplete;
		this.kroll = kroll;
		wifiManager = (WifiManager) TiApplication.getInstance()
				.getSystemService(Context.WIFI_SERVICE);
	}

	// Getter
	public BroadcastReceiver getBroadcastReceiverInstance() {
		return wifiEventReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				WifiScanEventListener.this.onReceive(context, intent);
			}
		};
	}

	public void onReceive(Context ctx, Intent intent) {
		Log.d(LCAT, "onReceive callback called.");
		String action = intent.getAction();
		Log.d(LCAT, "action=" + action);
		Log.d(LCAT,
				"=========================Wifi scanner has responsed===========================");
		if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
			KrollDict result = new KrollDict();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
					&& ctx.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				Log.e(LCAT, "permission ACCESS_COARSE_LOCATION not granted!!");
				result.put("error",
						"permission ACCESS_COARSE_LOCATION not granted!");
				onScanComplete.call(kroll, result);
				return;
			}
			Log.d(LCAT, "permission ACCESS_COARSE_LOCATION is granted.");
			List<ScanResult> scanResults = wifiManager.getScanResults();
			List<ScanResultProxy> resultList = new ArrayList<ScanResultProxy>();
			Log.i(LCAT, "Scan results available " + scanResults.size());
			for (ScanResult scanResult : scanResults) {
				ScanResultProxy proxy = new ScanResultProxy(scanResult);
				resultList.add(proxy);
			}
			if (onScanComplete != null) {
				result.put("scanResults", (resultList.isEmpty()) ? null
						: resultList.toArray());
				result.put("runtime", System.currentTimeMillis() - startTime);
				onScanComplete.call(kroll, result);
			}

		} else {
			Log.e(LCAT, "Bad action!!! " + action);
		}
	}

}
// from :
// https://github.com/divyekapoor/wifilocator/tree/73cb545345d253cc3c8da537e02941e45850c6c9/android/WifiLocation/src/in/ernet/iitr/divyeuec/sensors