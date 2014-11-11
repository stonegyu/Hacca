package com.broccoli.hacca.registerapi;

public interface OnRegisterAPIListener {
	void onSuccessRegister();
	void onFailRegister();
	void onDuplicatedRegister();
}
