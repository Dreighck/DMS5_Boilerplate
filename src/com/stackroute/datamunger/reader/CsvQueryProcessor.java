package com.stackroute.datamunger.reader;

import com.stackroute.datamunger.query.*;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.Restriction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvQueryProcessor implements QueryProcessingEngine {
    static final long serialVersionUID = 1L;

    private QueryParameter queryParameter = new QueryParameter();

    public CsvQueryProcessor() {
    }

    public CsvQueryProcessor(QueryParameter queryParameter) {
        this.queryParameter = queryParameter;
    }

    public QueryParameter getQueryParameter() {
        return queryParameter;
    }

    public void setQueryParameter(QueryParameter queryParameter) {
        this.queryParameter = queryParameter;
    }

    public DataSet getResultSet(QueryParameter queryParameter) throws IOException {

        DataSet dataSet = new DataSet();
        Header headerMap = new Header();
        RowDataTypeDefinitions r2d2 = new RowDataTypeDefinitions();
        Filter filter = new Filter();

        String fileName = queryParameter.getFileName();
        FileReader file= new FileReader(fileName);
        BufferedReader br = new BufferedReader(file);

        String headerLine = br.readLine();
        br.mark(1);
        String[] headers = headerLine.split(",");

        for (int i = 0; i < headers.length; i++) {
            headerMap.put(headers[i].trim(),i);
        }

        String fieldLine = br.readLine();
        String[] fields = fieldLine.split(",", headers.length);
        int indexR2D2=0;
        for (String field : fields) {
            //r2d2 is HashMap<int,string>
            r2d2.put(indexR2D2++, DataTypeDefinitions.getDataTypes(field));
        }


        long setRow = 1;
        String words;
        br.reset();

        while ((words = br.readLine()) != null) {
            boolean continueOn;
            List<Restriction> theRestrictions = queryParameter.getRestrictions();
            String[] pieces = words.split(",", headers.length);
            ArrayList<Boolean> booleans = new ArrayList<>();
            if (theRestrictions == null){continueOn = true;}
            else {
                for (Restriction theRestriction : theRestrictions) {
                    //Header stores in HashMap<String, Integer> so get returns the index
                    int index = headerMap.get(theRestriction.getPropertyName());
                    booleans.add(filter.evaluateExpression(pieces[index].trim(), theRestriction,  r2d2.get(index)));
                }
                continueOn = solveOperators(booleans, queryParameter.getLogicalOperators());
            }
            if (continueOn) {
                Row rowMap = new Row();
                List<String> theFields = queryParameter.getFields();
                for (String theField : theFields) {
                    if (theField.equals("*")) {
                        for (int j = 0; j < headers.length; j++) {
                            //rowMap stores in Hashmap<S,S>
                            rowMap.put(headers[j].trim(), pieces[j]);
                        }
                    } else {
                        rowMap.put(theField, pieces[headerMap.get(theField)]);
                    }
                }
                dataSet.put(setRow++, rowMap);
            }
        }
        br.close();
        return dataSet;
    }

    private boolean solveOperators(ArrayList<Boolean> booleans, List<String> operators) {

        if (booleans.size() == 1) {
            return booleans.get(0);

        } else if (booleans.size() == 2) {
            if (operators.get(0).matches("and"))
                return booleans.get(0) & booleans.get(1);
            else
                return booleans.get(0) | booleans.get(1);

        } else if(booleans.size()==3) {
            int i = operators.indexOf("and");
            switch(i) {
                case(-1): return booleans.get(0) | booleans.get(1) | booleans.get(2);
                case(0): return booleans.get(0) & booleans.get(1) | booleans.get(2);
                case(1): return booleans.get(0) | booleans.get(1) & booleans.get(2);
                default: return false;
            }
        }
        else return false;
    }
}
/*
 * This method will take QueryParameter object as a parameter which contains the
 * parsed query and will process and populate the ResultSet
 */
/*
 * initialize BufferedReader to read from the file which is mentioned in
 * QueryParameter. Consider Handling Exception related to file reading.
 */
/*
 * read the first line which contains the header. Please note that the headers
 * can contain spaces in between them. For eg: city, winner
 */
/*
 * read the next line which contains the first row of data. We are reading this
 * line so that we can determine the data types of all the fields. Please note
 * that ipl.csv file contains null value in the last column. If you do not
 * consider this while splitting, this might cause exceptions later
 */
/*
 * populate the header Map object from the header array. header map is having
 * data type <String,Integer> to contain the header and it's index.
 */
/*
 * We have read the first line of text already and kept it in an array. Now, we
 * can populate the RowDataTypeDefinition Map object. RowDataTypeDefinition map
 * is having data type <Integer,String> to contain the index of the field and
 * it's data type. To find the dataType by the field value, we will use
 * getDataType() method of DataTypeDefinitions class
 */


/*
 * once we have the header and dataTypeDefinitions maps populated, we can start
 * reading from the first line. We will read one line at a time, then check
 * whether the field values satisfy the conditions mentioned in the query,if
 * yes, then we will add it to the resultSet. Otherwise, we will continue to
 * read the next line. We will continue this till we have read till the last
 * line of the CSV file.
 */


/* reset the buffered reader so that it can start reading from the first line */

/*
 * skip the first line as it is already read earlier which contained the header
 */
/* read one line at a time from the CSV file till we have any lines left */
/*
 * once we have read one line, we will split it into a String Array. This array
 * will continue all the fields of the row. Please note that fields might
 * contain spaces in between. Also, few fields might be empty.
 */
/*
 * if there are where condition(s) in the query, test the row fields against
 * those conditions to check whether the selected row satifies the conditions
 */

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

/* return dataset object */