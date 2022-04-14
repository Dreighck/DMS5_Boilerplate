package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QueryParser {

    private QueryParameter queryParameter = new QueryParameter();

    public QueryParameter parseQuery(String queryString) {
        queryParameter.setFileName(getFileName(queryString));
        queryParameter.setBaseQuery(getBaseQuery(queryString));
        queryParameter.setOrderByFields(getOrderByFields(queryString));
        queryParameter.setGroupByFields(getGroupByFields(queryString));
        queryParameter.setFields(getFields(queryString));
        queryParameter.setAggregateFunctions(getAggregateFunctions(queryString));
        queryParameter.setRestrictions(getConditions(queryString));
        queryParameter.setLogicalOperators(getLogicalOperators(queryString));
        return queryParameter;
    }

    public String getFileName(String queryString){
        return queryString.split("from")[1].trim().split(" ")[0].trim();
    }

    public String getBaseQuery(String queryString){
        return queryString.split("order by | group by | where")[0].trim();
    }

    public List<String> getOrderByFields(String queryString) {
        List<String> list =null;
        if (queryString.contains(" order by ")) {
            list = new ArrayList<>();
            String[] separate = queryString.split(" from ")[1].split(" order by ");
            for(String s : separate){
                if (s.contains(" group by ")){
                    s = s.split(" group by ")[0];
                }
                if (!s.contains(getFileName(queryString))){
                    list.add(s);
                }
            }
        }
        return list;
    }

    public List<String> getGroupByFields(String queryString) {
        List<String> list=null;
        if (queryString.contains(" group by ")) {
            list = new ArrayList<>();
            String[] separate = queryString.split(" from ")[1].split(" group by ");
            for(String s : separate){
                if (s.contains(" order by ")){
                    s = s.split(" order by ")[0];
                }
                if (!s.contains(getFileName(queryString))){
                    list.add(s);
                }
            }
        }
        return list;
    }

    public List<String> getFields(String queryString){
        return Arrays.asList(queryString.toLowerCase().split("select")[1].trim()
                                        .split("from")[0].replace(" ","")
                                        .split(","));
    }

    public List<Restriction> getConditions(String queryString) {
        if(!queryString.contains("where"))
            return null;
        String[] tokens=queryString.trim().split("where");
        String[] conditions=tokens[1].trim().split(" order by | group by ")[0].trim().split(" and | or ");
        List<Restriction>restrictionList=new LinkedList<Restriction>();
        for (String s : conditions) {
            String condition;
            if(s.contains(">=")) {
                condition = ">=";
            } else if(s.contains("<=")) {
                condition = "<=";
            } else if(s.contains("!=")) {
                condition = "!=";
            } else if(s.contains(">")) {
                condition = ">";
            } else if(s.contains("<")) {
                condition = "<";
            } else if(s.contains("=")) {
                condition = "=";
            } else condition = "";
            String name = s.split(condition)[0].trim();
            String value = s.split(condition)[1].trim().replaceAll("'", "");
            Restriction restrictionInstance = new Restriction(name, value, condition);
            restrictionList.add(restrictionInstance);
        }
        return restrictionList;
    }

    public static String capitalize(String str) {
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);

    }


    public List<String> getLogicalOperators(String queryString){
        List<String> list = null;
        if (queryString.contains(" or ")||queryString.contains(" and "))
            list = new ArrayList<>();
        String[] pieces = queryString.split(" ");
        String[] operators = {"and","or"};
        for(String piece : pieces){
            for(String operator : operators){
                if(piece.equals(operator)) {
                    assert list != null;
                    list.add(operator);
                }
            }
        }
        return list;
    }

    public List<AggregateFunction> getAggregateFunctions(String queryString) {
        String[] Aggs = {"min(", "max(", "sum(", "count(", "avg("};
        List<AggregateFunction> type = new ArrayList<>();
        String[] query = queryString.split("select ")[1].split(" from ")[0].split(",");
        for(String functions : query) {
            for(String s : Aggs) {
                if (functions.contains(s)) {
                    String function = s.substring(0, s.length() - 1);
                    String field = functions.replace(s, "").replace(")", "").trim();
                    AggregateFunction a = new AggregateFunction(field,function);
                    type.add(a);
                }
            }
        }
        return type;
    }
}

