package com.stackroute.datamunger.reader;

import com.stackroute.datamunger.query.DataSet;
import com.stackroute.datamunger.query.parser.QueryParameter;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface QueryProcessingEngine {
	DataSet getResultSet(QueryParameter queryParameter) throws Exception;
}
