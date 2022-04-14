package com.stackroute.datamunger.query.parser;

/*
 * This class is used for storing name of field, condition and value for 
 * each conditions
 * */
public class Restriction {
	private final String propertyName;
	private final String propertyValue;
	private final String condition;
	// Write logic for constructor

	public Restriction(String propertyName, String propertyValue, String condition) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.condition = condition;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public String getCondition() {return condition;	}


	@Override
	public String toString() {
		return "Restriction{" +
				"name='" + propertyName + '\'' +
				", value='" + propertyValue + '\'' +
				", condition='" + condition + '\'' +
				'}';
	}
}
