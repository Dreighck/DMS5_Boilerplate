package com.stackroute.datamunger.query;

import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.QueryParser;
import com.stackroute.datamunger.reader.CsvQueryProcessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Queue;

public class Query {

	/*
	 * This method will: 
	 * 1.parse the query and populate the QueryParameter object
	 * 2.Based on the type of query, it will select the appropriate Query processor.
	 * 
	 * In this example, we are going to work with only one Query Processor which is
	 * CsvQueryProcessor, which can work with select queries containing zero, one or
	 * multiple conditions
	 */
	@SuppressWarnings("rawtypes")
	public HashMap executeQuery(String queryString) throws IOException {
		/* instantiate QueryParser class */
		QueryParser queryParser = new QueryParser();
		/*
		 * call parseQuery() method of the class by passing the queryString which will
		 * return object of QueryParameter
		 */
		QueryParameter queryParameter = queryParser.parseQuery(queryString);
		/*
		 * Check for Type of Query based on the QueryParameter object. In this
		 * assignment, we will process only queries containing zero, one or multiple
		 * where conditions i.e. conditions without aggregate functions, order by clause
		 * or group by clause
		 */
		String QUERY_TYPE="WHERE";
		if (queryParameter.getOrderByFields() != null || queryParameter.getAggregateFunctions() != null
				|| queryParameter.getGroupByFields() != null) {
			QUERY_TYPE = null;
		}
		queryParameter.setQUERY_TYPE(QUERY_TYPE);
		/*
		 * call the getResultSet() method of CsvQueryProcessor class by passing the
		 * QueryParameter Object to it. This method is supposed to return resultSet
		 * which is a HashMap
		 */

		CsvQueryProcessor csvQueryProcessor = new CsvQueryProcessor(queryParameter);
		return csvQueryProcessor.getResultSet(queryParameter);
	}

}
