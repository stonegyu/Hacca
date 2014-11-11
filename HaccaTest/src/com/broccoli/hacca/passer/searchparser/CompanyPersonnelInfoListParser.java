package com.broccoli.hacca.passer.searchparser;

import java.util.ArrayList;

import com.broccoli.hacca.pageinfo.CompanyPersonnelInfo;
import com.broccoli.hacca.pageinfo.PageInfo;
import com.broccoli.hacca.passer.JsonParser;
import com.broccoli.hacca.passer.Parser;

public class CompanyPersonnelInfoListParser implements ParsingInfoType{

	@Override
	public PageInfo parseInfo(String parsingValue) {

		Parser parser = new Parser(new JsonParser(parsingValue));
		
		ArrayList<CompanyPersonnelInfo> companyPersonnelInfos = new ArrayList<CompanyPersonnelInfo>();
		
		ArrayList<String> companyLoginIdList = getParseInfo(parser, "loginId");
		ArrayList<String> companyAreaList = getParseInfo(parser, "companyArea");
		ArrayList<String> companyBusinessTypeList = getParseInfo(parser, "companyBusinessType");
		ArrayList<String> companyHomePageList = getParseInfo(parser, "companyHomePage");
		ArrayList<String> companyMailList = getParseInfo(parser, "companyMail");
		ArrayList<String> companyNameList = getParseInfo(parser, "companyName");
		ArrayList<String> companyRecruitPeriodList = getParseInfo(parser, "companyRecruitPeriod");
		ArrayList<String> companyRecruitVolumeList = getParseInfo(parser, "companyRecruitVolume");
		ArrayList<String> companySalaryList = getParseInfo(parser, "companySalary");
		ArrayList<String> companySizeList = getParseInfo(parser, "companySize");
		ArrayList<String> companyWorkTypeList = getParseInfo(parser, "companyWorkType");
		
		for(int i=0;i<companyLoginIdList.size();i++){
			CompanyPersonnelInfo companyPersonnelInfo = new CompanyPersonnelInfo();
			
			companyPersonnelInfo.setLoginId(companyLoginIdList.get(i));
			companyPersonnelInfo.setCompanyArea(companyAreaList.get(i));
			companyPersonnelInfo.setCompanyBusinessType(companyBusinessTypeList.get(i));
			companyPersonnelInfo.setCompanyHomePage(companyHomePageList.get(i));
			companyPersonnelInfo.setCompanyMail(companyMailList.get(i));
			companyPersonnelInfo.setCompanyName(companyNameList.get(i));
			companyPersonnelInfo.setCompanyRecruitPeriod(companyRecruitPeriodList.get(i));
			companyPersonnelInfo.setCompanyRecruitVolume(companyRecruitVolumeList.get(i));
			companyPersonnelInfo.setCompanySalary(companySalaryList.get(i));
			companyPersonnelInfo.setCompanySize(companySizeList.get(i));
			companyPersonnelInfo.setCompanyWorkType(companyWorkTypeList.get(i));
			
			companyPersonnelInfos.add(companyPersonnelInfo);
		}
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCompanyPersonnelInfos(companyPersonnelInfos);
		
		return pageInfo;
	}
	
	private ArrayList<String> getParseInfo(Parser parser,String parsingKey){
		
		return parser.parse(parsingKey);
	}
}
