package com.stackroute.datamunger.query;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/*
 * Implementation of DataTypeDefinitions class. This class contains a method getDataTypes() 
 * which will contain the logic for getting the datatype for a given field value. This
 * method will be called from QueryProcessors.   
 * In this assignment, we are going to use Regular Expression to find the 
 * appropriate data type of a field. 
 * Integers: should contain only digits without decimal point 
 * Double: should contain digits as well as decimal point 
 * Date: Dates can be written in many formats in the CSV file. 
 * However, in this assignment,we will test for the following date formats('dd/mm/yyyy',
 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
 */
public class DataTypeDefinitions {
	private HashMap<String,Integer> dataTypes;
	private HashMap<Integer,String> result;

	public DataTypeDefinitions(){}
	public DataTypeDefinitions(HashMap<String,Integer> dataTypes) {
		this.dataTypes = dataTypes;
	}

	public HashMap<Integer, String> getResult() {
		return result;
	}
	public void setResult(HashMap<Integer, String> result) {
		this.result = result;
	}

	public HashMap<String, Integer> getDataTypes() {
		return dataTypes;
	}
	public void setDataTypes(HashMap<String, Integer> dataTypes) {
		this.dataTypes = dataTypes;
	}

	//method stub
	public Object getDataType(HashMap<String,Integer> dataTypes){
		HashMap<Integer,String> dataHashMap = new HashMap<>();
		String[] dataTypesArray;
		dataTypesArray = dataTypes.keySet().toArray(new String[0]);
		this.dataTypes = dataTypes;
		for (int i=0;i< dataTypesArray.length;i++) {
			if (dataTypesArray[i].matches("[0-9]+")) {
				dataTypesArray[i] = "java.lang.Integer";
			} else if (dataTypesArray[i].matches("[\\d*.?]+")) {
				dataTypesArray[i] = "java.lang.Double";
			} else if (dataTypesArray[i].isEmpty()) {
				dataTypesArray[i] = "java.lang.Object";
			} else if (isDate(dataTypesArray[i])) {
				dataTypesArray[i] = "java.util.Date";
			} else {
				dataTypesArray[i] = "java.lang.String";
			}
		}
		for(int j=0;j<dataTypesArray.length;j++){
			dataHashMap.put(j,dataTypesArray[j]);
		}
		this.result = dataHashMap;
		return dataHashMap;
	}
	public static boolean isDate(String piece){
		// checking for date format dd/mm/yyyy
		// checking for date format mm/dd/yyyy
		if(piece.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}"))
			return true;
			// checking for date format dd-mon-yy
		else if(piece.matches("[0-9]{2}-[a-z]{3}-[0-9]{2}"))
			return true;
			// checking for date format dd-mon-yyyy
		else if(piece.matches("[0-9]{2}-[a-z]{3}-[0-9]{4}"))
			return true;
			// checking for date format dd-month-yy
		else if(piece.matches("[0-9]{2}/[a-z]*/[0-9]{2}"))
			return true;
			// checking for date format dd-month-yyyy
		else if(piece.matches("[0-9]{2}-[a-z]*-[0-9]{4}"))
			return true;
			// checking for date format yyyy-mm-dd
		else return piece.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}");
	}
}
