package com.broccoli.hacca.internalstorageapi;

import android.content.Context;
import android.content.SharedPreferences;

public class InternalStorageAPIImpl implements InternalStorageAPI{
	private Context context;
	private final String LoginIDStoreKey = "LOGINID_KEY";
	private final String DeviceIDStoreKey = "DEVICEID_KEY";
	private final String sharedPreferenceName = "STORE_NAME";

	public InternalStorageAPIImpl(Context context){
		this.context = context;
	}
	
	@Override
	public String getLoginId() {
		return getStoredInfo(LoginIDStoreKey);
	}

	@Override
	public String getDeviceId() {
		return getStoredInfo(DeviceIDStoreKey);
	}

	@Override
	public void storeLoginID(String loginID) {
		storeInfo(LoginIDStoreKey, loginID);
	}

	@Override
	public void storeDeviceID(String deviceID) {
		storeInfo(DeviceIDStoreKey, deviceID);
	}
	
	private void storeInfo(String key, String info) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				sharedPreferenceName, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, info);
		editor.commit();
	}

	private String getStoredInfo(String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				sharedPreferenceName, context.MODE_PRIVATE);
		return sharedPreferences.getString(key, null);
	}

}
