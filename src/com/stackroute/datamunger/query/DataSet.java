package com.stackroute.datamunger.query;

import java.io.Serializable;
import java.util.LinkedHashMap;

//This class will be acting as the DataSet containing multiple rows
public class DataSet extends LinkedHashMap<Long, Row> implements Serializable {
	private static final long serialVersionUID = 1L;
	private LinkedHashMap<Long,Row> dataSet = new LinkedHashMap();

	public DataSet(){}
	public DataSet(LinkedHashMap<Long,Row> dataSet){
		this.dataSet=dataSet;
	}

	public LinkedHashMap<Long, Row> getDataSet() {
		return dataSet;
	}

	public void setDataSet(LinkedHashMap<Long, Row> dataSet) {
		this.dataSet = dataSet;
	}
}
