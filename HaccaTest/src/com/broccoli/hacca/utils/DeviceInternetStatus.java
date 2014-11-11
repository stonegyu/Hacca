package com.broccoli.hacca.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class DeviceInternetStatus{

	private ConnectivityManager connectManager;
	private Context context;

	public DeviceInternetStatus(Context context) {
		this.context = context;
	}

	public boolean isConnectedInternet() {
		connectManager = (ConnectivityManager) context
				.getSystemService(context.CONNECTIVITY_SERVICE);

		if (connectManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState() == State.CONNECTED
				|| connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						.getState() == State.CONNECTED)
			return true;
		else {
			return false;
		}
	}

}
