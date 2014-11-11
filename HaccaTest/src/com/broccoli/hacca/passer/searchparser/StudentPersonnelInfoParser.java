package com.broccoli.hacca.passer.searchparser;

import android.util.Log;

import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.pageinfo.StudentPersonnelInfo;
import com.broccoli.hacca.passer.JsonParser;
import com.broccoli.hacca.passer.Parser;

public class StudentPersonnelInfoParser implements ParsingInfoType{

	private final String TAG="StudentPersonnelInfoParser";
	
	@Override
	public PageInfo parseInfo(String parsingValue) {
		
		Parser parser = new Parser(new JsonParser(parsingValue));
		
		StudentPersonnelInfo studentPersonnelInfo = new StudentPersonnelInfo();
		
		studentPersonnelInfo.setLoginId(getParseInfo(parser, "loginId"));
		studentPersonnelInfo.setStudentName(getParseInfo(parser, "studentName"));
		studentPersonnelInfo.setStudentCareer(getParseInfo(parser, "studentCareer"));
		studentPersonnelInfo.setStudentHope(getParseInfo(parser, "studentHope"));
		studentPersonnelInfo.setStudentBlog(getParseInfo(parser, "studentBlog"));
		studentPersonnelInfo.setStudentMail(getParseInfo(parser, "studentMail"));
		studentPersonnelInfo.setStudentAbility(getParseInfo(parser, "studentAbility"));
		studentPersonnelInfo.setStudentAge(getParseInfo(parser, "studentAge"));
		studentPersonnelInfo.setStudentSex(getParseInfo(parser, "studentSex"));
		studentPersonnelInfo.setStudentDepartment(getParseInfo(parser, "studentDepartment"));
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setStudentPersonnelInfo(studentPersonnelInfo);
		
		return pageInfo;
	}
	
	private String getParseInfo(Parser parser,String parsingKey){
		String result;
		
		try{
			result = parser.parse(parsingKey).get(0);
		}catch(IndexOutOfBoundsException e){
			result="";
		}
		
		return result;
	}
}
