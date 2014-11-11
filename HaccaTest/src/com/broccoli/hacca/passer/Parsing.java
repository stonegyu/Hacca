package com.broccoli.hacca.passer;

import java.util.ArrayList;

public interface Parsing {
	ArrayList<String> parse(Object encodedParsingValue,String parsingKey);
	Object encodeParsingValue();
}
