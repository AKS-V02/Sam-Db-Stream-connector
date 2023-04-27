package com.rds.utils;

import java.util.Map;

import com.amazonaws.services.rdsdata.model.ExecuteStatementResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

    public ExecuteStatementResult UpdateRecordOfTable(){
        return null;
    } 

    private String getAddRecordSqlStatement(String tableName, JsonArray columnNameAsJsonArray, JsonArray columnValueAsJsonArray) {
        String statement = "INSERT INTO "+tableName+" (";
        String comaseperatedColName = "";
        // String comaseperatedbindVarName = "";
        for(JsonElement columnObj:columnNameAsJsonArray){
            comaseperatedColName = comaseperatedColName + columnObj.getAsString()+", ";
            // comaseperatedbindVarName = comaseperatedbindVarName +":"+columnObj.getAsString()+", ";
        } 
        // return statement+comaseperatedColName.replaceAll(", $",")")+" VALUES (" 
        //                 +comaseperatedbindVarName.replaceAll(", $",");");
        return statement+comaseperatedColName.replaceAll(", $",")")+" VALUES "+getSqlParameterSet(columnNameAsJsonArray, columnValueAsJsonArray);
    }

    private String getSqlParameterSet( JsonArray columnNameAsJsonArray, JsonArray columnValueAsJsonArray){
        // List<List<SqlParameter>> parameterSet = new ArrayList<>();
        String parameterSet = "";
        for(JsonElement valueObj:columnValueAsJsonArray){
            // List<SqlParameter> record = new ArrayList<>();
            String record = "(";
            for(JsonElement colname :columnNameAsJsonArray){
                if(valueObj.getAsJsonObject().get(colname.getAsString()) == null){
                    record = record +"NULL" + ", ";
                    // record.add(new SqlParameter()
                    //         .withName(colname.getAsString())
                    //         .withValue(new Field().withIsNull(true)));
                } else if(valueObj.getAsJsonObject().get(colname.getAsString()).getAsJsonPrimitive().isNumber()){
                    record = record +valueObj.getAsJsonObject().get(colname.getAsString()).getAsString()+ ", ";
                    // record.add(new SqlParameter()
                    //         .withName(colname.getAsString())
                    //         .withValue(new Field()
                    //                 .withLongValue(valueObj
                    //                 .getAsJsonObject()
                    //                 .get(colname.getAsString())
                    //                 .getAsLong())));
                } else {
                    record = record +"'"+valueObj.getAsJsonObject().get(colname.getAsString()).getAsString()+"'"+ ", ";
                    // record.add(new SqlParameter()
                    //         .withName(colname.getAsString())
                    //         .withValue(new Field()
                    //                 .withStringValue(valueObj
                    //                 .getAsJsonObject()
                    //                 .get(colname.getAsString())
                    //                 .getAsString())));
                }
            }
            // parameterSet.add(record);
            parameterSet = parameterSet + record.replaceAll(", $","), ");
        }
        return parameterSet.replaceAll(", $",";");
    }
}
