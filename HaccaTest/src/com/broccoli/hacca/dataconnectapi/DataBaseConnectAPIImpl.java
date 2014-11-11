package com.broccoli.hacca.dataconnectapi;

import android.util.Log;


public class DataBaseConnectAPIImpl implements DataBaseConnectAPI,OnDataBaseConnectWorkerListner{
	private final String TAG = "DataBaseConnectAPIImpl";
	
	private OnDataBaseConnectAPIListener dataBaseConnectAPIListener;

	public DataBaseConnectAPIImpl(OnDataBaseConnectAPIListener dataBaseConnectAPIListener){
		this.dataBaseConnectAPIListener = dataBaseConnectAPIListener;
	}
	
	@Override
	public void connectDataBase(String url) {
		
		Log.i(TAG, url+"");
		
		DataBaseConnectWorker worker = new DataBaseConnectWorker(this);
		worker.execute(url);
	}

	@Override
	public void onCompleted(String result) {
		
		Log.i(TAG, result+"");
		
		//맨앞에 ASCII CODE 65279가 붙음
		result = result.replace(String.valueOf((char)65279), "" );
				
		if(result.equals("null")){
			dataBaseConnectAPIListener.onFailToConnectDataBase();
		}else{
			dataBaseConnectAPIListener.onSuccessToConnectDataBase(result);
		}
	}
}
