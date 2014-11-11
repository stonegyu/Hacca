package com.broccoli.hacca.loginapi;

import android.util.Log;

import com.broccoli.hacca.dataconnectapi.DataBaseConnectAPI;
import com.broccoli.hacca.dataconnectapi.DataBaseConnectAPIImpl;
import com.broccoli.hacca.dataconnectapi.OnDataBaseConnectAPIListener;
import com.broccoli.hacca.passer.JsonParser;
import com.broccoli.hacca.passer.Parser;
import com.broccoli.hacca.urlfactory.UrlType;
import com.broccoli.hacca.urlfactory.UrlDataStorage;
import com.broccoli.hacca.urlfactory.UrlFactory;
import com.broccoli.hacca.urlfactory.UrlParameterType;

public class LoginAPIImpl implements LoginAPI,OnDataBaseConnectAPIListener{
	private final String TAG = "LoginAPIImpl";
	private OnLoginAPIListener loginAPIListener;

	public LoginAPIImpl(OnLoginAPIListener loginAPIListener){
		this.loginAPIListener = loginAPIListener;
	}
	
	@Override
	public void login(String loginId, String deviceId) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(UrlType.LOGIN);
		urlDataStorage.setParameter(UrlParameterType.LOGINID, loginId);
		urlDataStorage.setParameter(UrlParameterType.DEVICEID, deviceId);
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);
		
		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
		
		Log.i(TAG, url);
	}

	@Override
	public void onSuccessToConnectDataBase(String result) {
		//맨앞에 ASCII CODE 65279가 붙음
		result = result.replace(String.valueOf((char)65279), "" );
		
		if(result.equals("false")){
			loginAPIListener.onNotRegisteredMember();
		}else{
			
			String writtenLoginId = new Parser(new JsonParser(result)).parse("loginId").get(0);
			String writtenDeviceId = new Parser(new JsonParser(result)).parse("deviceId").get(0);
		
			loginAPIListener.onSuccessLogin(writtenLoginId,writtenDeviceId);
		}
	}

	@Override
	public void onFailToConnectDataBase() {
		loginAPIListener.onFailLogin();
	}

}
