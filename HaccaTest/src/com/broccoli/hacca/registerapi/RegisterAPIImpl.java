package com.broccoli.hacca.registerapi;

import android.util.Log;

import com.broccoli.hacca.dataconnectapi.DataBaseConnectAPI;
import com.broccoli.hacca.dataconnectapi.DataBaseConnectAPIImpl;
import com.broccoli.hacca.dataconnectapi.OnDataBaseConnectAPIListener;
import com.broccoli.hacca.pageinfo.CommentInfo;
import com.broccoli.hacca.pageinfo.CompanyPersonnelInfo;
import com.broccoli.hacca.pageinfo.NoticeBoardInfo;
import com.broccoli.hacca.pageinfo.StudentPersonnelInfo;
import com.broccoli.hacca.urlfactory.UrlDataStorage;
import com.broccoli.hacca.urlfactory.UrlFactory;
import com.broccoli.hacca.urlfactory.UrlParameterType;
import com.broccoli.hacca.urlfactory.UrlType;

public class RegisterAPIImpl implements RegisterAPI,OnDataBaseConnectAPIListener{

	private final String TAG="RegisterAPIImpl";
	
	private OnRegisterAPIListener registerAPIListener;

	public RegisterAPIImpl(OnRegisterAPIListener registerAPIListener){
		this.registerAPIListener = registerAPIListener;
	}
	
	@Override
	public void registerStudent(StudentPersonnelInfo studentPersonnelInfo) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(
				UrlType.STUDENT_REGISTER);
		urlDataStorage.setParameter(UrlParameterType.LOGINID, studentPersonnelInfo.getLoginId());
		urlDataStorage.setParameter(UrlParameterType.DEVICEID, studentPersonnelInfo.getDeviceId());
		urlDataStorage.setParameter(UrlParameterType.STUDENT_NAME, studentPersonnelInfo.getStudentName());
		urlDataStorage.setParameter(UrlParameterType.STUDENT_CAREER, studentPersonnelInfo.getStudentCareer());
		urlDataStorage.setParameter(UrlParameterType.STUDENT_HOPE, studentPersonnelInfo.getStudentHope());
		urlDataStorage.setParameter(UrlParameterType.STUDENT_BLOG, studentPersonnelInfo.getStudentBlog());
		urlDataStorage.setParameter(UrlParameterType.STUDENT_MAIL, studentPersonnelInfo.getStudentMail());
		urlDataStorage.setParameter(UrlParameterType.STUDENT_ABILITY, studentPersonnelInfo.getStudentAbility());
		urlDataStorage.setParameter(UrlParameterType.STUDENT_AGE, studentPersonnelInfo.getStudentAge());
		urlDataStorage.setParameter(UrlParameterType.STUDENT_SEX, studentPersonnelInfo.getStudentSex());
		urlDataStorage.setParameter(UrlParameterType.STUDENT_DEPARTMENT, studentPersonnelInfo.getStudentDepartment());
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);
		
		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}
	
	@Override
	public void registerCompany(CompanyPersonnelInfo companyPersonnelInfo) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(
				UrlType.COMPANY_REGISTER);
		urlDataStorage.setParameter(UrlParameterType.LOGINID, companyPersonnelInfo.getLoginId());
		urlDataStorage.setParameter(UrlParameterType.DEVICEID, companyPersonnelInfo.getDeviceId());
		urlDataStorage.setParameter(UrlParameterType.COMPANY_NAME, companyPersonnelInfo.getCompanyName());
		urlDataStorage.setParameter(UrlParameterType.COMPANY_AREA, companyPersonnelInfo.getCompanyArea());
		urlDataStorage.setParameter(UrlParameterType.COMPANY_BUSINESSTYPE, companyPersonnelInfo.getCompanyBusinessType());
		urlDataStorage.setParameter(UrlParameterType.COMPANY_HOMEPAGE, companyPersonnelInfo.getCompanyHomePage());
		urlDataStorage.setParameter(UrlParameterType.COMPANY_MAIL, companyPersonnelInfo.getCompanyMail());
		urlDataStorage.setParameter(UrlParameterType.COMPANY_PASSWORD, companyPersonnelInfo.getCompanyPassword());
		urlDataStorage.setParameter(UrlParameterType.COMPANY_RECRUITPERIOD, companyPersonnelInfo.getCompanyRecruitPeriod());
		urlDataStorage.setParameter(UrlParameterType.COMPANY_RECRUITVOLUME, companyPersonnelInfo.getCompanyRecruitVolume());
		urlDataStorage.setParameter(UrlParameterType.COMPANY_SALARY, companyPersonnelInfo.getCompanySalary());
		urlDataStorage.setParameter(UrlParameterType.COMPANY_SIZE, companyPersonnelInfo.getCompanySize());
		urlDataStorage.setParameter(UrlParameterType.COMPANY_WORKTYPE, companyPersonnelInfo.getCompanyWorkType());
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);
		
		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}
	
	@Override
	public void registerComment(CommentInfo commentInfo) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(
				UrlType.COMMENT_REGISTER);
		urlDataStorage.setParameter(UrlParameterType.COMMENT_PROFESSOR_LOGINID, commentInfo.getProfessorLoginId());
		urlDataStorage.setParameter(UrlParameterType.COMMENT_STUDENT_LOGINID, commentInfo.getStudentLoginId());
		urlDataStorage.setParameter(UrlParameterType.COMMENT, commentInfo.getComment());
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);
		
		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}
	
	@Override
	public void registerNoticeBoard(NoticeBoardInfo noticeBoardInfo) {
		UrlDataStorage urlDataStorage = new UrlDataStorage(
				UrlType.NOTICEBOARD_REGISTER);
		urlDataStorage.setParameter(UrlParameterType.NOTICEBOARD_NUMBER, noticeBoardInfo.getNumber());
		urlDataStorage.setParameter(UrlParameterType.NOTICEBOARD_TITLE, noticeBoardInfo.getTitle());
		urlDataStorage.setParameter(UrlParameterType.NOTICEBOARD_CONTENT, noticeBoardInfo.getContent());
		urlDataStorage.setParameter(UrlParameterType.NOTICEBOARD_DATE, noticeBoardInfo.getDate());
		urlDataStorage.setParameter(UrlParameterType.NOTICEBOARD_CATEGORY, noticeBoardInfo.getCategory());
		urlDataStorage.setParameter(UrlParameterType.LOGINID, noticeBoardInfo.getLoginId());
		
		
		String url = UrlFactory.getInstance().getUrl(urlDataStorage);
		
		DataBaseConnectAPI dataBaseConnectAPI = new DataBaseConnectAPIImpl(this);
		dataBaseConnectAPI.connectDataBase(url);
	}

	@Override
	public void onSuccessToConnectDataBase(String result) {
		
		//맨앞에 ASCII CODE 65279가 붙음
		result = result.replace(String.valueOf((char)65279), "" );
		
		if(result.equals("true")) {
			registerAPIListener.onSuccessRegister();
		}else if(result.equals("duplicated")){
			registerAPIListener.onDuplicatedRegister();
		}else if(result.equals("false")){
			registerAPIListener.onFailRegister();
		}
	}

	@Override
	public void onFailToConnectDataBase() {
		registerAPIListener.onFailRegister();
	}
}
