package com.broccoli.hacca.passer.searchparser;

import java.util.ArrayList;

import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.pageinfo.StudentPersonnelInfo;
import com.broccoli.hacca.passer.JsonParser;
import com.broccoli.hacca.passer.Parser;

public class StudentPersonnelInfoListParser implements ParsingInfoType{

	@Override
	public PageInfo parseInfo(String parsingValue) {

		Parser parser = new Parser(new JsonParser(parsingValue));
		
		ArrayList<StudentPersonnelInfo> studentPersonnelInfos = new ArrayList<StudentPersonnelInfo>();
		
		ArrayList<String> studentLoginIdList = getParseInfo(parser, "loginId");
		ArrayList<String> studentNameList = getParseInfo(parser, "studentName");
		ArrayList<String> studentCareerList = getParseInfo(parser, "studentCareer");
		ArrayList<String> studentHopeList = getParseInfo(parser, "studentHope");
		ArrayList<String> studentBlogList = getParseInfo(parser, "studentBlog");
		ArrayList<String> studentMailList = getParseInfo(parser, "studentMail");
		ArrayList<String> studentAbilityList = getParseInfo(parser, "studentAbility");
		ArrayList<String> studentAgeList = getParseInfo(parser, "studentAge");
		ArrayList<String> studentSexList = getParseInfo(parser, "studentSex");
		ArrayList<String> studentDepartmentList = getParseInfo(parser, "studentDepartment");
		
		for(int i=0;i<studentNameList.size();i++){
			StudentPersonnelInfo studentPersonnelInfo = new StudentPersonnelInfo();
			
			studentPersonnelInfo.setLoginId(studentLoginIdList.get(i));
			studentPersonnelInfo.setStudentName(studentNameList.get(i));
			studentPersonnelInfo.setStudentCareer(studentCareerList.get(i));
			studentPersonnelInfo.setStudentHope(studentHopeList.get(i));
			studentPersonnelInfo.setStudentBlog(studentBlogList.get(i));
			studentPersonnelInfo.setStudentMail(studentMailList.get(i));
			studentPersonnelInfo.setStudentAbility(studentAbilityList.get(i));
			studentPersonnelInfo.setStudentAge(studentAgeList.get(i));
			studentPersonnelInfo.setStudentSex(studentSexList.get(i));
			studentPersonnelInfo.setStudentDepartment(studentDepartmentList.get(i));
			
			studentPersonnelInfos.add(studentPersonnelInfo);
		}
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setStudentPersonnelInfos(studentPersonnelInfos);
		
		return pageInfo;
	}
	
	private ArrayList<String> getParseInfo(Parser parser,String parsingKey){
		
		return parser.parse(parsingKey);
	}
}
