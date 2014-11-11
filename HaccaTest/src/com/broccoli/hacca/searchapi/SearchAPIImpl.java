package com.broccoli.hacca.searchapi;

import android.util.Log;

import com.broccoli.hacca.dataconnectapi.DataBaseConnectAPI;
import com.broccoli.hacca.dataconnectapi.DataBaseConnectAPIImpl;
import com.broccoli.hacca.dataconnectapi.OnDataBaseConnectAPIListener;
import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.passer.searchparser.ParsingInfoType;
import com.broccoli.hacca.urlfactory.UrlDataStorage;
import com.broccoli.hacca.urlfactory.UrlFactory;
import com.broccoli.hacca.urlfactory.UrlParameterType;
import com.broccoli.hacca.urlfactory.UrlType;

public class SearchAPIImpl implements SearchAPI,OnDataBaseConnectAPIListener {

	private final String TAG="SearchAPIImpl";
	
	private ParsingInfoType parsingInfo;
	private OnSearchAPIListener searchAPIListener;

	public SearchAPIImpl(OnSearchAPIListener searchAPIListener,ParsingInfoType parsingInfoType){
		this.searchAPIListener = searchAPIListener;
		this.parsingInfo = parsingInfoType;
	}
	
	@Override
	public void searchStudentInfo(String loginId) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(UrlType.STUDENT_SEARCH);
		urlDataStorage.setParameter(UrlParameterType.LOGINID, loginId);
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);

		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}
	
	@Override
	public void searchStudentInfoList(String limit) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(UrlType.STUDENT_SEARCH);
		urlDataStorage.setParameter(UrlParameterType.LIMIT, limit);
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);

		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}
	
	@Override
	public void searchStudentInfoList(String searchText,String limit) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(UrlType.STUDENT_SEARCH);
		urlDataStorage.setParameter(UrlParameterType.SEARCH_TEXT, searchText);
		urlDataStorage.setParameter(UrlParameterType.LIMIT, limit);
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);

		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}
	
	@Override
	public void searchCompanyInfo(String loginId, String companyPassword) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(UrlType.COMPANY_SEARCH);
		urlDataStorage.setParameter(UrlParameterType.LOGINID, loginId);
		urlDataStorage.setParameter(UrlParameterType.COMPANY_PASSWORD, companyPassword);
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);

		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}
	
	@Override
	public void searchStudentComment(String studentLoginId) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(UrlType.COMMENT_SEARCH);
		urlDataStorage.setParameter(UrlParameterType.COMMENT_STUDENT_LOGINID, studentLoginId);
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);

		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}
	
	@Override
	public void searchCompanyInfoList(String limit) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(UrlType.COMPANY_SEARCH);
		urlDataStorage.setParameter(UrlParameterType.LIMIT, limit);
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);

		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}

	@Override
	public void searchCompanyInfoList(String searchText, String limit) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(UrlType.COMPANY_SEARCH);
		urlDataStorage.setParameter(UrlParameterType.SEARCH_TEXT, searchText);
		urlDataStorage.setParameter(UrlParameterType.LIMIT, limit);
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);

		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}
	
	@Override
	public void searchNoticeBoard(String category, String limit) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(UrlType.NOTICEBOARD_SEARCH);
		urlDataStorage.setParameter(UrlParameterType.NOTICEBOARD_CATEGORY, category);
		urlDataStorage.setParameter(UrlParameterType.LIMIT, limit);
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);

		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}

	@Override
	public void searchNoticeBoard(String category, String searchText,
			String limit) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(UrlType.NOTICEBOARD_SEARCH);
		urlDataStorage.setParameter(UrlParameterType.NOTICEBOARD_CATEGORY, category);
		urlDataStorage.setParameter(UrlParameterType.SEARCH_TEXT, searchText);
		urlDataStorage.setParameter(UrlParameterType.LIMIT, limit);
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);

		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}

	@Override
	public void onSuccessToConnectDataBase(String result) {
		
		Log.i(TAG, result+"");
		
		PageInfo pageInfo = parsingInfo.parseInfo(result);
		searchAPIListener.onSuccessSearch(pageInfo);
	}

	@Override
	public void onFailToConnectDataBase() {
		searchAPIListener.onFailSearch();
	}
}
