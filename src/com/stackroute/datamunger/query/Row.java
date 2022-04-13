package com.stackroute.datamunger.query;

import java.io.Serializable;
import java.util.HashMap;

//Contains the row object as ColumnName/Value. Hence, HashMap is being used
public class Row extends HashMap<String, String> implements Serializable {
	private static final long serialVersionUID = 1L;
	public Row(){}
}
