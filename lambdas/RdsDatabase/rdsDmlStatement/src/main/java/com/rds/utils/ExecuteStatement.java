package com.rds.utils;

import java.util.Map;

import com.amazonaws.services.rdsdata.model.ExecuteStatementResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rds.dao.DbConnection;

public class ExecuteStatement {
    private static final Map<String, String> headers = Map.of("Content-Type", "application/json", 
                                                        "Access-Control-Allow-Origin", "*", 
                                                        "Access-Control-Allow-Methods", "*");
    

    private DbConnection dbClient;
    
    public ExecuteStatement() {
        this.dbClient = new DbConnection();

    } 

    public static Map<String, String> getHeaders() {
        return headers;
    }

    public ExecuteStatementResult addRecordsIntoTable(String tableName, JsonArray columnNameAsJsonArray, JsonArray columnValueAsJsonArray){
        
       return this.dbClient.executeSqlStatement(getAddRecordSqlStatement(tableName, columnNameAsJsonArray, columnValueAsJsonArray));
    }

    private String getAddRecordSqlStatement(String tableName, JsonArray columnNameAsJsonArray, JsonArray columnValueAsJsonArray) {
        //String statement = "INSERT INTO "+tableName+" (";
        StringBuilder statement=new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        // String comaseperatedColName = "";
        // String comaseperatedbindVarName = "";
        for(JsonElement columnObj:columnNameAsJsonArray){
            statement.append(columnObj.getAsString()).append(", ");
            // comaseperatedColName = comaseperatedColName + columnObj.getAsString()+", ";
            // comaseperatedbindVarName = comaseperatedbindVarName +":"+columnObj.getAsString()+", ";
        } 
        statement.replace(statement.length()-2,statement.length(),")")
                .append(" VALUES ")
                .append(getSqlParameterSet(columnNameAsJsonArray, columnValueAsJsonArray));
        // return statement+comaseperatedColName.replaceAll(", $",")")+" VALUES (" 
        //                 +comaseperatedbindVarName.replaceAll(", $",");");
        // return statement+comaseperatedColName.replaceAll(", $",")")+" VALUES "+getSqlParameterSet(columnNameAsJsonArray, columnValueAsJsonArray);
        return statement.toString();
    }

    private String getSqlParameterSet( JsonArray columnNameAsJsonArray, JsonArray columnValueAsJsonArray){
        // List<List<SqlParameter>> parameterSet = new ArrayList<>();
        StringBuilder parameterSet=new StringBuilder();
        // String parameterSet = "";
        for(JsonElement valueObj:columnValueAsJsonArray){
            // List<SqlParameter> record = new ArrayList<>();
            parameterSet.append("(");
            // String record = "(";
            for(JsonElement colname :columnNameAsJsonArray){

                parameterSet.append(convertValueForSqlStatement(valueObj.getAsJsonObject().get(colname.getAsString()))).append(", ");
                // if(valueObj.getAsJsonObject().get(colname.getAsString()) == null){
                //     record = record +"NULL" + ", ";
                //     // record.add(new SqlParameter()
                //     //         .withName(colname.getAsString())
                //     //         .withValue(new Field().withIsNull(true)));
                // } else if(valueObj.getAsJsonObject().get(colname.getAsString()).getAsJsonPrimitive().isNumber()){
                //     record = record +valueObj.getAsJsonObject().get(colname.getAsString()).getAsString()+ ", ";
                //     // record.add(new SqlParameter()
                //     //         .withName(colname.getAsString())
                //     //         .withValue(new Field()
                //     //                 .withLongValue(valueObj
                //     //                 .getAsJsonObject()
                //     //                 .get(colname.getAsString())
                //     //                 .getAsLong())));
                // } else {
                //     record = record +"'"+valueObj.getAsJsonObject().get(colname.getAsString()).getAsString()+"'"+ ", ";
                //     // record.add(new SqlParameter()
                //     //         .withName(colname.getAsString())
                //     //         .withValue(new Field()
                //     //                 .withStringValue(valueObj
                //     //                 .getAsJsonObject()
                //     //                 .get(colname.getAsString())
                //     //                 .getAsString())));
                // }
            }
            // parameterSet.add(record);
            parameterSet.replace(parameterSet.length()-2,parameterSet.length(),"), ");
            // parameterSet = parameterSet + record.replaceAll(", $","), ");
        }
        return parameterSet.replace(parameterSet.length()-2,parameterSet.length(),";").toString();
    }


    // public static void main(String[] args) throws Exception {
    //     String input = "{\"updateColumnValue\":{\"id\":3,\"game\":\"name game\"},\"conditionColumnValue\":{\"new\":\"good\",\"right\":\"bad\"}}";
    //     JsonObject inputObj = JsonParser.parseString(input).getAsJsonObject();
    //     // JsonObject conditionColumnValue = JsonParser.parseString("{\"new\":\"good\",\"right\":\"bad\"}").getAsJsonObject();
    //     System.out.println(updateRecordOfTable( "tableName", inputObj.get("conditionColumnValue").getAsJsonObject(), 
    //                                                                     inputObj.get("updateColumnValue").getAsJsonObject()));
    //     System.out.println(inputObj.get("conditionColumnValue").toString());
    // }

    public ExecuteStatementResult updateRecordOfTable(String tableName, JsonObject conditionColumnValue, JsonObject updateColumnValue){
            StringBuilder statement=new StringBuilder("UPDATE "+tableName+" SET ");  
                for(String columnName:updateColumnValue.keySet()){
                    statement.append(columnName).append("=")
                        .append(convertValueForSqlStatement(updateColumnValue.get(columnName)))
                        .append(", ");
            
                }
                statement.replace(statement.length()-2,statement.length()," ").append("WHERE ");
                for(String condition:conditionColumnValue.keySet()){
                statement.append(condition).append("=")
                    .append(convertValueForSqlStatement(conditionColumnValue.get(condition)))
                    .append(" AND ");
                }
            statement.replace(statement.length()-5,statement.length(),";");
            return this.dbClient.executeSqlStatement(statement.toString());
          }
          public String convertValueForSqlStatement(JsonElement valueObj){
            if(valueObj == null){
              return "NULL";
            } else if(valueObj.getAsJsonPrimitive().isNumber()){
              return valueObj.getAsString();
            } else {
              return "'"+valueObj.getAsString()+"'";
            }
          }
}
