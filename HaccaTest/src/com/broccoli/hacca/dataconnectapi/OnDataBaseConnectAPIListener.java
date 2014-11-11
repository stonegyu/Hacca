package com.broccoli.hacca.dataconnectapi;

public interface OnDataBaseConnectAPIListener {
	void onSuccessToConnectDataBase(String result);
	void onFailToConnectDataBase();
}
