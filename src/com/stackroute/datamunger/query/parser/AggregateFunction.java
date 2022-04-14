package com.stackroute.datamunger.query.parser;


public class AggregateFunction {
	private final String field;
	private final String function;

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
	public String getFunction() {
		return function;
	}

}
