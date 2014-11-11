package com.broccoli.hacca.passer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class JsonParser implements Parsing {

	private final String TAG = "JsonParser";
	
	private String parsingValue;

	public JsonParser(String parsingValue) {
		this.parsingValue = parsingValue;
	}

	private ArrayList<String> parseJson(JSONArray parsingValues,
			String parsingKey) {
		
		ArrayList<String> valueList = new ArrayList<String>();

		try {
			for (int i = 0; i < parsingValues.length(); i++) {
				valueList.add(parsingValues.getJSONObject(i).getString(
						parsingKey));
			}
		} catch (JSONException e) {

		}

		return valueList;
	}

	@Override
	public ArrayList<String> parse(Object encodedParsingValue,String parsingKey) {

		return parseJson((JSONArray) encodedParsingValue, parsingKey);
	}

	@Override
	public Object encodeParsingValue() {
		
		try {
			return new JSONArray(parsingValue);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
