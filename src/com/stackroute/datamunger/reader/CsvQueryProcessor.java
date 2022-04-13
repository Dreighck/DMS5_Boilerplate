package com.stackroute.datamunger.reader;

import com.stackroute.datamunger.query.*;
import com.stackroute.datamunger.query.parser.QueryParameter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CsvQueryProcessor implements QueryProcessingEngine {
	static final long serialVersionUID = 1L;
	/*
	 * This method will take QueryParameter object as a parameter which contains the
	 * parsed query and will process and populate the ResultSet
	 */
	private QueryParameter queryParameter = new QueryParameter();
	private DataSet resultSet = new DataSet();

	public CsvQueryProcessor(QueryParameter queryParameter) {
		this.queryParameter = queryParameter;
	}
	public QueryParameter getQueryParameter() {
		return queryParameter;
	}
	public void setQueryParameter(QueryParameter queryParameter) {
		this.queryParameter = queryParameter;
	}

	public DataSet getResultSet(QueryParameter queryParameter) throws FileNotFoundException,IOException {
		/*
		 * initialize BufferedReader to read from the file which is mentioned in
		 * QueryParameter. Consider Handling Exception related to file reading.
		 */
		DataSet dataSet = new DataSet();
		String fileName = queryParameter.getFileName(queryParameter);
		FileOutputStream fileOutputStream;
		ObjectOutputStream oos;
		try{
			fileOutputStream = new FileOutputStream(fileName);
			oos= new ObjectOutputStream(fileOutputStream);}
		catch (IOException e ){
			System.out.println("IO exception line 35");
		}
		FileReader file;
		try{
			file = new FileReader(fileName);}
		catch(FileNotFoundException e){
			System.out.println("File not found.");
			file=null;
		}
		BufferedReader br = new BufferedReader(file);
		/*
		 * read the first line which contains the header. Please note that the headers
		 * can contain spaces in between them. For eg: city, winner
		 */
		String headers = null;
		try{
		headers = br.readLine();}
		catch (IOException e){
			System.out.println("IOException");
		}
		assert headers != null;
		int lengthHeaders = headers.split(",").length;
		/*
		 * read the next line which contains the first row of data. We are reading this
		 * line so that we can determine the data types of all the fields. Please note
		 * that ipl.csv file contains null value in the last column. If you do not
		 * consider this while splitting, this might cause exceptions later
		 */
		String line = null;
		try{
			line = br.readLine();}
		catch (IOException e){
			System.out.println("IOException");
		}
		/*
		 * populate the header Map object from the header array. header map is having
		 * data type <String,Integer> to contain the header and it's index.
		 */
		HashMap<String,Integer> headerHashmap = new HashMap<String,Integer>();
		assert line != null;
		String[] header = line.replace(" ","").split(",");
		for (int i = 0; i < header.length; i++) headerHashmap.put(header[i],i);
		Header headerMap = new Header(headerHashmap);
		/*
		 * We have read the first line of text already and kept it in an array. Now, we
		 * can populate the RowDataTypeDefinition Map object. RowDataTypeDefinition map
		 * is having data type <Integer,String> to contain the index of the field and
		 * it's data type. To find the dataType by the field value, we will use
		 * getDataType() method of DataTypeDefinitions class
		 */
		DataTypeDefinitions dtd = new DataTypeDefinitions(headerMap);
		HashMap<Integer,String> definitions = dtd.getResult();
		RowDataTypeDefinitions r2dt = new RowDataTypeDefinitions(definitions);

		/*
		 * once we have the header and dataTypeDefinitions maps populated, we can start
		 * reading from the first line. We will read one line at a time, then check
		 * whether the field values satisfy the conditions mentioned in the query,if
		 * yes, then we will add it to the resultSet. Otherwise, we will continue to
		 * read the next line. We will continue this till we have read till the last
		 * line of the CSV file.
		 */
		Query q = new Query();
		int row =0;
		br.reset();
		List<Integer> listRow = new ArrayList<>();
		do{
			String words = br.readLine();
			q.executeQuery(words);
			if(queryParameter.getQUERY_TYPE().equals("WHERE")){
				listRow.add(row);
			}
		}while (br.readLine()!=null);




		/* reset the buffered reader so that it can start reading from the first line */
		br.reset();
		/*
		 * skip the first line as it is already read earlier which contained the header
		 */
		br.readLine();

		/* read one line at a time from the CSV file till we have any lines left */
		while(br.readLine()!=null) {
			String words = br.readLine();

			/*
			 * once we have read one line, we will split it into a String Array. This array
			 * will continue all the fields of the row. Please note that fields might
			 * contain spaces in between. Also, few fields might be empty.
			 */
			String[] pieces = words	.split("select ")[1]
									.split(" from")[0]
									.replace(" ","")
									.split(",");
			/*
			 * if there are where condition(s) in the query, test the row fields against
			 * those conditions to check whether the selected row satifies the conditions
			 */
			int i=0;
			for(String piece : pieces){
				if(piece.toLowerCase().equals("where")){
					System.out.println(pieces[i+1]+ " " + pieces[i+2]+ " " + pieces[i+3]);
				}
			}

			/*
			 * from QueryParameter object, read one condition at a time and evaluate the
			 * same. For evaluating the conditions, we will use evaluateExpressions() method
			 * of Filter class. Please note that evaluation of expression will be done
			 * differently based on the data type of the field. In case the query is having
			 * multiple conditions, you need to evaluate the overall expression i.e. if we
			 * have OR operator between two conditions, then the row will be selected if any
			 * of the condition is satisfied. However, in case of AND operator, the row will
			 * be selected only if both of them are satisfied.
			 */

			/*
			 * check for multiple conditions in where clause for eg: where salary>20000 and
			 * city=Bangalore for eg: where salary>20000 or city=Bangalore and dept!=Sales
			 */

			/*
			 * if the overall condition expression evaluates to true, then we need to check
			 * if all columns are to be selected(select *) or few columns are to be
			 * selected(select col1,col2). In either of the cases, we will have to populate
			 * the row map object. Row Map object is having type <String,String> to contain
			 * field Index and field value for the selected fields. Once the row object is
			 * populated, add it to DataSet Map Object. DataSet Map object is having type
			 * <Long,Row> to hold the rowId (to be manually generated by incrementing a Long
			 * variable) and it's corresponding Row Object.
			 */
		}
		/* return dataset object */
		return dataSet;
	}

	}
