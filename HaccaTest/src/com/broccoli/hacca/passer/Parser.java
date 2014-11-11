package com.broccoli.hacca.passer;

import java.util.ArrayList;

public class Parser {

	private Parsing parsing;

	public Parser(Parsing parsing){
		this.parsing = parsing;
	}
	
	public ArrayList<String> parse(String parsingKey){
		return parsing.parse(parsing.encodeParsingValue(),parsingKey);
	}
}