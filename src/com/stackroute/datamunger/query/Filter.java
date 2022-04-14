package com.stackroute.datamunger.query;

import com.stackroute.datamunger.query.parser.Restriction;

import java.text.SimpleDateFormat;

//This class contains methods to evaluate expressions
public class Filter {

	/*
	 * The evaluateExpression() method of this class is responsible for evaluating
	 * the expressions mentioned in the query. It has to be noted that the process
	 * of evaluating expressions will be different for different data types. there
	 * are 6 operators that can exist within a query i.e. >=,<=,<,>,!=,= This method
	 * should be able to evaluate all of them.
	 * Note: while evaluating string expressions, please handle uppercase and lowercase
	 *
	 */
	public boolean evaluateExpression(Restriction restriction, String fieldValue, String dataType) {
		switch (restriction.getCondition()) {
			case "=":
				return isEqualTo(fieldValue, restriction.getPropertyValue(), dataType);
			case "!=":
				return isNotEqual(fieldValue, restriction.getPropertyValue(), dataType);
			case ">":
				return isGreaterThan(fieldValue, restriction.getPropertyValue(), dataType);
			case ">=":
				return isGreaterThanOrEqualTo(fieldValue, restriction.getPropertyValue(), dataType);
			case "<":
				return isLessThan(fieldValue, restriction.getPropertyValue(), dataType);
			default:
				return isLessThanOrEqualTo(fieldValue, restriction.getPropertyValue(), dataType);
		}
	}

	//Method containing implementation of equalTo operator
	public boolean isEqualTo(String val1, String val2, String dataType) {
		switch (dataType) {
			case "java.lang.Integer":
				return Integer.parseInt(val1) == Integer.parseInt(val2);
			case "java.lang.Double":
				return Double.parseDouble(val1) == Double.parseDouble(val2);
			case "java.util.Date":
				SimpleDateFormat formatter = new SimpleDateFormat(dateFormat(val1));
				try {
					return (formatter.parse(val1).compareTo(formatter.parse(val2))) == 0;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			case "java.util.Object":
				return false;
			default:
				return val1.compareTo(val2) == 0;
		}
	}
	//Method containing implementation of greaterThan operator
	private boolean isGreaterThan(String val1, String val2, String dataType){
		switch (dataType) {
			case "java.lang.Integer":
				return Integer.parseInt(val1) > Integer.parseInt(val2);
			case "java.lang.Double":
				return Double.parseDouble(val1) > Double.parseDouble(val2);
			case "java.util.Date":
				SimpleDateFormat formatter = new SimpleDateFormat(dateFormat(val1));
				try {
					return formatter.parse(val1).compareTo(formatter.parse(val2)) > 0;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			case "java.util.Object":
				return false;
			default:
				return val1.compareTo(val2) == 0;
		}
	}
	//Method containing implementation of notEqualTo operator
	private boolean isNotEqual(String val1, String val2, String dataType) {
		return !isEqualTo(val1, val2, dataType);
	}
	//Method containing implementation of greaterThanOrEqualTo operator
	private boolean isGreaterThanOrEqualTo(String val1, String val2, String dataType) {
		return isEqualTo(val1, val2, dataType)|isGreaterThan(val1,val2,dataType);
	}
	//Method containing implementation of lessThan operator
	private boolean isLessThan(String val1, String val2, String dataType) {
		return !(isEqualTo(val1, val2, dataType)|isGreaterThan(val1,val2,dataType));
	}
	//Method containing implementation of lessThanOrEqualTo operator
	private boolean isLessThanOrEqualTo(String val1, String val2, String dataType) {
		return (isLessThan(val1, val2, dataType)|isEqualTo(val1,val2,dataType));
	}

	//from DataTypeDefinitions
	private String dateFormat(String date) {
		String format;
		if(date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}"))
			format = "dd/mm/yyyy";
		else if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}"))
			format = "yyyy-mm-dd";
		else if(date.matches("[0-9]{2}-[a-z]{3}-[0-9]{2}"))
			format = "dd-mon-yy";
		else if(date.matches("[0-9]{2}-[a-z]{3}-[0-9]{4}"))
			format ="dd-mon-yyyy";
		else if(date.matches("[0-9]{2}-[a-z]*-[0-9]{2}"))
			format = "dd-month-yy";
		else
			format ="dd-month-yyyy";
		return format;
	}
}
