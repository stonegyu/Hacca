package com.broccoli.hacca.urlfactory;

public enum UrlType {

	LOGIN("login.php?"), STUDENT_REGISTER("student_register.php?"), STUDENT_SEARCH(
			"student_search.php?"), COMPANY_REGISTER("company_register.php?"), COMPANY_SEARCH(
			"company_search.php?"), COMMENT_SEARCH("comment_search.php?"), COMMENT_REGISTER(
			"comment_register.php?"), NOTICEBOARD_SEARCH(
			"noticeBoard_search.php?"), NOTICEBOARD_REGISTER(
			"noticeBoard_register.php?");

	private String name;

	private UrlType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
