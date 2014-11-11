package com.broccoli.hacca.passer.searchparser;

import com.broccoli.hacca.pageinfo.CompanyPersonnelInfo;
import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.pageinfo.StudentPersonnelInfo;
import com.broccoli.hacca.passer.JsonParser;
import com.broccoli.hacca.passer.Parser;

public class CompanyPersonnelInfoParser implements ParsingInfoType{

	@Override
	public PageInfo parseInfo(String parsingValue) {
		
		Parser parser = new Parser(new JsonParser(parsingValue));
		
		CompanyPersonnelInfo companyPersonnelInfo = new CompanyPersonnelInfo();
		
		companyPersonnelInfo.setLoginId(getParseInfo(parser, "loginId"));
		companyPersonnelInfo.setCompanyArea(getParseInfo(parser, "companyArea"));
		companyPersonnelInfo.setCompanyBusinessType(getParseInfo(parser, "companyBusinessType"));
		companyPersonnelInfo.setCompanyHomePage(getParseInfo(parser, "companyHomePage"));
		companyPersonnelInfo.setCompanyMail(getParseInfo(parser, "companyMail"));
		companyPersonnelInfo.setCompanyName(getParseInfo(parser, "companyName"));
		companyPersonnelInfo.setCompanyRecruitPeriod(getParseInfo(parser, "companyRecruitPeriod"));
		companyPersonnelInfo.setCompanyRecruitVolume(getParseInfo(parser, "companyRecruitVolume"));
		companyPersonnelInfo.setCompanySalary(getParseInfo(parser, "companySalary"));
		companyPersonnelInfo.setCompanySize(getParseInfo(parser, "companySize"));
		companyPersonnelInfo.setCompanyWorkType(getParseInfo(parser, "companyWorkType"));
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCompanyPersonnelInfo(companyPersonnelInfo);
		
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
