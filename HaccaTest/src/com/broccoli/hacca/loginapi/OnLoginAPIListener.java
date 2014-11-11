package com.broccoli.hacca.loginapi;

public interface OnLoginAPIListener {
	void onSuccessLogin(String writtenLoginId,String writtenDeviceId);
	void onNotRegisteredMember();
	void onFailLogin();
}
