package com.broccoli.hacca.passer.searchparser;

import java.util.ArrayList;

import com.broccoli.hacca.pageinfo.CommentInfo;
import com.broccoli.hacca.pageinfo.CompanyPersonnelInfo;
import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.pageinfo.StudentPersonnelInfo;
import com.broccoli.hacca.passer.JsonParser;
import com.broccoli.hacca.passer.Parser;

public class CommentInfoListParser implements ParsingInfoType{

	@Override
	public PageInfo parseInfo(String parsingValue) {

		Parser parser = new Parser(new JsonParser(parsingValue));
		
		ArrayList<CommentInfo> commentInfos = new ArrayList<CommentInfo>();
		
		ArrayList<String> professorLoginIdList = getParseInfo(parser, "professorLoginId");
		ArrayList<String> studentLoginIdList = getParseInfo(parser, "studentLoginId");
		ArrayList<String> professorNameList = getParseInfo(parser, "professorName");
		ArrayList<String> studentNameList = getParseInfo(parser, "studentName");
		ArrayList<String> commentList = getParseInfo(parser, "comment");
		
		for(int i=0;i<professorNameList.size();i++){
			CommentInfo commentInfo = new CommentInfo();
			
			try{
				commentInfo.setProfessorLoginId(professorLoginIdList.get(i));
			}catch(IndexOutOfBoundsException e){
				
			}
			
			try{
				commentInfo.setStudentLoginId(studentLoginIdList.get(i));
			}catch(IndexOutOfBoundsException e){
				
			}
			
			try{
				commentInfo.setProfessorName(professorNameList.get(i));
			}catch (IndexOutOfBoundsException e){
				
			}
			
			try{
				commentInfo.setStudentName(studentNameList.get(i));
			}catch(IndexOutOfBoundsException e){
				
			}
			
			try{
				commentInfo.setComment(commentList.get(i));
			}catch(IndexOutOfBoundsException e){
				
			}
			
			commentInfos.add(commentInfo);
		}
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCommentInfos(commentInfos);
		
		return pageInfo;
	}
	
	private ArrayList<String> getParseInfo(Parser parser,String parsingKey){
		
		return parser.parse(parsingKey);
	}

}
