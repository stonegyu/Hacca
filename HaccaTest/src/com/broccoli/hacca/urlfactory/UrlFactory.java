package com.broccoli.hacca.urlfactory;

import java.util.Iterator;
import java.util.Map;

public class UrlFactory {
	private final String serverUrl="http://xargus.cafe24.com/hacca/";
	private static UrlFactory urlFactory = new UrlFactory();
	
	private UrlFactory(){
		
	}
	
	public static UrlFactory getInstance(){
		return urlFactory;
	}
	
	public String getUrl(UrlDataStorage urlDataStorage){
		
		return serverUrl+urlDataStorage.getUrlType().getName()+makeUrlParameters(urlDataStorage);
		
	}
	
	private String makeUrlParameters(UrlDataStorage dataStorage) {
		StringBuffer buffer = new StringBuffer();
		Map<String, String> map = dataStorage.getUrlDataSet();

		Iterator<String> iterator = map.keySet().iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();
			buffer.append(key + "=" + map.get(key));
			buffer.append("&");
		}

		return buffer.toString();
	}
}
