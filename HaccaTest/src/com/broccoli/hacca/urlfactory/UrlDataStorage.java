package com.broccoli.hacca.urlfactory;

import java.util.HashMap;
import java.util.Map;

public class UrlDataStorage {
	private Map<String, String> urlDataSet = new HashMap<String, String>();
	private UrlType urlType;
	
	public UrlDataStorage(UrlType urlType){
		this.urlType = urlType;
	}

	public Map<String, String> getUrlDataSet() {
		return urlDataSet;
	}

	public void setParameter(UrlParameterType urlParameterType,String value){
		urlDataSet.put(urlParameterType.getName(), value);
	}

	public UrlType getUrlType() {
		return urlType;
	}
}
