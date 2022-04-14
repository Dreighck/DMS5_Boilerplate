package com.stackroute.datamunger.query;

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


	public DataTypeDefinitions(){}

	//method stub
	public static String getDataTypes(String dataTypes){

			if (dataTypes.matches("[0-9]+")) {
				dataTypes = "java.lang.Integer";
			} else if (dataTypes.matches("[\\d*.?]+")) {
				dataTypes = "java.lang.Double";
			} else if (dataTypes.isEmpty()) {
				dataTypes = "java.lang.Object";
			} else if (isDate(dataTypes)) {
				dataTypes = "java.util.Date";
			} else {
				dataTypes = "java.lang.String";
			}
		return dataTypes;
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
