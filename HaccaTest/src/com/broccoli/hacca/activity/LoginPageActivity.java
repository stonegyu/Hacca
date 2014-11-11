package com.broccoli.hacca.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.broccoli.hacca.internalstorageapi.InternalStorageAPI;
import com.broccoli.hacca.internalstorageapi.InternalStorageAPIImpl;
import com.broccoli.hacca.loginapi.LoginAPI;
import com.broccoli.hacca.loginapi.LoginAPIImpl;
import com.broccoli.hacca.loginapi.OnLoginAPIListener;

public class LoginPageActivity extends Activity implements OnLoginAPIListener {

	private String deviceId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		InternalStorageAPI storageAPI = new InternalStorageAPIImpl(this);
		deviceId = storageAPI.getDeviceId();
		
		if (storageAPI.getLoginId() != null) {
			LoginAPI loginAPI = new LoginAPIImpl(this);
			loginAPI.login(storageAPI.getLoginId(), storageAPI.getDeviceId());
		} else {

		}
	}

	@Override
	public void onSuccessLogin(String writtenLoginId, String writtenDeviceId) {
		if(this.deviceId.equals(writtenDeviceId)){
			//same device
		}else{
			//other device registered
		}
	}

	@Override
	public void onFailLogin() {
		Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNotRegisteredMember() {
		Toast.makeText(this, "등록된 회원이 아닙니다.", Toast.LENGTH_SHORT).show();
	}

}
