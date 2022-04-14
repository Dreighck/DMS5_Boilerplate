package com.stackroute.datamunger.query;

import java.io.Serializable;
import java.util.LinkedHashMap;

//This class will be acting as the DataSet containing multiple rows
public class DataSet extends LinkedHashMap<Long, Row> implements Serializable {
	private static final long serialVersionUID = 1L;

}
