package com.broccoli.hacca.urlfactory;

public enum UrlParameterType {
	LOGINID("loginId"), DEVICEID("deviceId"), STUDENT_NAME("studentName"), STUDENT_CAREER(
			"studentCareer"), STUDENT_HOPE("studentHope"), STUDENT_BLOG(
			"studentBlog"), STUDENT_MAIL("studentMail"), STUDENT_ABILITY(
			"studentAbility"), STUDENT_AGE("studentAge"), STUDENT_SEX(
			"studentSex"), STUDENT_DEPARTMENT("studentDepartment"), COMPANY_NAME(
			"companyName"), COMPANY_SALARY("companySalary"), COMPANY_RECRUITVOLUME(
			"companyRecruitVolume"), COMPANY_RECRUITPERIOD(
			"companyRecruitPeriod"), COMPANY_MAIL("companyMail"), COMPANY_HOMEPAGE(
			"companyHomePage"), COMPANY_PASSWORD("companyPassword"), COMPANY_AREA(
			"companyArea"), COMPANY_SIZE("companySize"), COMPANY_BUSINESSTYPE(
			"companyBusinessType"), COMPANY_WORKTYPE("companyWorkType"), COMMENT_STUDENT_LOGINID(
			"studentLoginId"), COMMENT_PROFESSOR_LOGINID("professorLoginId"), COMMENT(
			"comment"), SEARCH_TEXT("searchText"),LIMIT("limit"),NOTICEBOARD_CATEGORY("category"),
			NOTICEBOARD_NUMBER("number"),NOTICEBOARD_TITLE("title"),NOTICEBOARD_CONTENT("content"),
			NOTICEBOARD_DATE("date");

	private String name;

	private UrlParameterType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
