package com.broccoli.hacca.registerapi;

import com.broccoli.hacca.pageinfo.CommentInfo;
import com.broccoli.hacca.pageinfo.CompanyPersonnelInfo;
import com.broccoli.hacca.pageinfo.NoticeBoardInfo;
import com.broccoli.hacca.pageinfo.StudentPersonnelInfo;

public interface RegisterAPI {
	void registerStudent(StudentPersonnelInfo studentPersonnelInfo);
	void registerCompany(CompanyPersonnelInfo companyPersonnelInfo);
	void registerComment(CommentInfo commentInfo);
	void registerNoticeBoard(NoticeBoardInfo noticeBoardInfo);
}
