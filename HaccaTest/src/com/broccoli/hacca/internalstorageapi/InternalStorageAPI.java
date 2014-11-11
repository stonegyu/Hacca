package com.broccoli.hacca.internalstorageapi;

public interface InternalStorageAPI {
	String getLoginId();
	String getDeviceId();
	void storeLoginID(String loginID);
	void storeDeviceID(String deviceID);
}
