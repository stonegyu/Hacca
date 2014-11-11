package com.broccoli.hacca.pageinfo;

import java.util.ArrayList;

public class PageInfo {

	private StudentPersonnelInfo studentPersonnelInfo;
	private CompanyPersonnelInfo companyPersonnelInfo;
	private ArrayList<StudentPersonnelInfo> studentPersonnelInfos;
	private ArrayList<CompanyPersonnelInfo> companyPersonnelInfos;
	private ArrayList<CommentInfo> commentInfos;
	private ArrayList<NoticeBoardInfo> noticeBoardInfos;
	
	public ArrayList<NoticeBoardInfo> getNoticeBoardInfos() {
		return noticeBoardInfos;
	}

	public void setNoticeBoardInfos(ArrayList<NoticeBoardInfo> noticeBoardInfos) {
		this.noticeBoardInfos = noticeBoardInfos;
	}

	public ArrayList<CompanyPersonnelInfo> getCompanyPersonnelInfos() {
		return companyPersonnelInfos;
	}

	public void setCompanyPersonnelInfos(
			ArrayList<CompanyPersonnelInfo> companyPersonnelInfos) {
		this.companyPersonnelInfos = companyPersonnelInfos;
	}

	public ArrayList<CommentInfo> getCommentInfos() {
		return commentInfos;
	}

	public void setCommentInfos(ArrayList<CommentInfo> commentInfos) {
		this.commentInfos = commentInfos;
	}

	public ArrayList<StudentPersonnelInfo> getStudentPersonnelInfos() {
		return studentPersonnelInfos;
	}

	public void setStudentPersonnelInfos(
			ArrayList<StudentPersonnelInfo> studentPersonnelInfos) {
		this.studentPersonnelInfos = studentPersonnelInfos;
	}

	public CompanyPersonnelInfo getCompanyPersonnelInfo() {
		return companyPersonnelInfo;
	}

	public void setCompanyPersonnelInfo(CompanyPersonnelInfo companyPersonnelInfo) {
		this.companyPersonnelInfo = companyPersonnelInfo;
	}

	public StudentPersonnelInfo getStudentPersonnelInfo() {
		return studentPersonnelInfo;
	}

	public void setStudentPersonnelInfo(StudentPersonnelInfo studentPersonnelInfo) {
		this.studentPersonnelInfo = studentPersonnelInfo;
	}
}
