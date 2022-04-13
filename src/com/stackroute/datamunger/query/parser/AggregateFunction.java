package com.stackroute.datamunger.query.parser;


public class AggregateFunction {
	private String field;
	private String function;

	public AggregateFunction(String field, String function) {
		this.field=field;
		this.function = function;
	}
	@Override
	public String toString() {
		return "AggregateFunction{" +
				"field='" + field + '\'' +
				", function='" + function + '\'' +
				'}';
	}
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
}
