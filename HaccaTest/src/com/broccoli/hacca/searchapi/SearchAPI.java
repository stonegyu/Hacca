package com.broccoli.hacca.searchapi;

public interface SearchAPI {
	void searchStudentInfo(String loginId);
	void searchStudentInfoList(String limit);
	void searchStudentInfoList(String searchText,String limit);
	void searchCompanyInfo(String loginId,String companyPassword);
	void searchCompanyInfoList(String limit);
	void searchCompanyInfoList(String searchText,String limit);
	void searchStudentComment(String studentLoginId);
	void searchNoticeBoard(String category,String limit);
	void searchNoticeBoard(String category,String searchText,String limit);
}
