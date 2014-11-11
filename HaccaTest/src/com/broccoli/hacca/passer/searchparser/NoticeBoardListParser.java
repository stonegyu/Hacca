package com.broccoli.hacca.passer.searchparser;

import java.util.ArrayList;

import com.broccoli.hacca.pageinfo.NoticeBoardInfo;
import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.passer.JsonParser;
import com.broccoli.hacca.passer.Parser;

public class NoticeBoardListParser implements ParsingInfoType{

	@Override
	public PageInfo parseInfo(String parsingValue) {

		Parser parser = new Parser(new JsonParser(parsingValue));
		
		ArrayList<NoticeBoardInfo> noticeBoardInfos = new ArrayList<NoticeBoardInfo>();
		
		ArrayList<String> numberList = getParseInfo(parser, "number");
		ArrayList<String> loginIdList = getParseInfo(parser, "loginId");
		ArrayList<String> professorNameList = getParseInfo(parser, "professorName");
		ArrayList<String> titleList = getParseInfo(parser, "title");
		ArrayList<String> contentList = getParseInfo(parser, "content");
		ArrayList<String> dateList = getParseInfo(parser, "date");
		ArrayList<String> categoryList = getParseInfo(parser, "category");
		
		for(int i=0;i<professorNameList.size();i++){
			NoticeBoardInfo noticeBoardInfo = new NoticeBoardInfo();
			
			noticeBoardInfo.setNumber(numberList.get(i));
			noticeBoardInfo.setLoginId(loginIdList.get(i));
			noticeBoardInfo.setProfessorName(professorNameList.get(i));
			noticeBoardInfo.setTitle(titleList.get(i));
			noticeBoardInfo.setContent(contentList.get(i));
			noticeBoardInfo.setDate(dateList.get(i));
			noticeBoardInfo.setCategory(categoryList.get(i));
			
			noticeBoardInfos.add(noticeBoardInfo);
		}
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setNoticeBoardInfos(noticeBoardInfos);
		
		return pageInfo;
	}
	
	private ArrayList<String> getParseInfo(Parser parser,String parsingKey){
		
		return parser.parse(parsingKey);
	}
}
