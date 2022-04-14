package com.stackroute.datamunger.query.parser;

import java.util.List;



public class QueryParameter {
	private String fileName;
	private List<String> fields;
	private List<Restriction> restrictions;
	private String baseQuery;
	@SuppressWarnings("FieldCanBeLocal")
	private String QUERY_TYPE;
	private List<AggregateFunction> aggregateFunctions;
	private List<String> logicalOperators;
	private List<String> groupByFields;
	private List<String> orderByFields;

	public String getFileName(QueryParameter queryParameter) {
		return fileName;
	}
	public String getFileName(){ return fileName;}
	public List<String> getFields() {
		return fields;
	}
	public List<Restriction> getRestrictions() {
		return restrictions;
	}
	public String getBaseQuery() {
		return baseQuery;
	}
	public List<AggregateFunction> getAggregateFunctions() {
		return aggregateFunctions;
	}
	public List<String> getLogicalOperators() {
		return logicalOperators;
	}
	public String getQUERY_TYPE() {return null;	}
	public List<String> getGroupByFields() {
		return groupByFields;
	}
	public List<String> getOrderByFields() {
		return orderByFields;
	}
	public void setFileName(String fileName) {this.fileName = fileName;	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}
	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}

	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;}

	public void setLogicalOperators(List<String> logicalOperators) {
		this.logicalOperators = logicalOperators;
	}
	public void setQUERY_TYPE(String QUERY_TYPE) {this.QUERY_TYPE = QUERY_TYPE;}
	public void setGroupByFields(List<String> groupByFields) {
		this.groupByFields = groupByFields;
	}
	public void setOrderByFields(List<String> orderByFields) {
		this.orderByFields = orderByFields;
	}
}
