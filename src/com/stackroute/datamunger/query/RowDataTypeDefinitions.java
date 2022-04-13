package com.stackroute.datamunger.query;

import com.stackroute.datamunger.query.parser.QueryParameter;

import java.util.HashMap;

//This class will be used to store the column data types as columnIndex/DataType
public class RowDataTypeDefinitions extends HashMap<Integer, String>{

	private static final long serialVersionUID = 1L;
	QueryParameter qp;
	DataTypeDefinitions dtd;
	HashMap<Integer,String> storage;


	public RowDataTypeDefinitions(HashMap<Integer, String> storage){
		this.storage=storage;
	}

	public HashMap<Integer, String> getStorage() {
		return storage;
	}

	public void setStorage(HashMap<Integer, String> storage) {
		this.storage = storage;
	}
}
