package com.rds.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazon.rdsdata.client.ExecutionResult;
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

    public ExecutionResult addRecordsIntoTable(String tableName, JsonArray columnNameAsJsonArray, JsonArray columnValueAsJsonArray){
        List<JsonObject> parameterSet = new ArrayList<>();
        for(JsonElement valueObj:columnValueAsJsonArray){
            parameterSet.add(valueObj.getAsJsonObject());
        }
       return this.dbClient.executeSqlStatementWithParameterSet(getAddRecordSqlStatement(tableName, columnNameAsJsonArray), parameterSet);
    }

    private String getAddRecordSqlStatement(String tableName, JsonArray columnNameAsJsonArray) {
        String statement = "INSERT INTO "+tableName+" (";
        String comaseperatedColName = "";
        String comaseperatedbindVarName = "";
        for(JsonElement columnObj:columnNameAsJsonArray){
            comaseperatedColName = comaseperatedColName + columnObj.getAsString()+", ";
            comaseperatedbindVarName = comaseperatedbindVarName +":"+columnObj.getAsString()+", ";
        } 
        return statement+comaseperatedColName.replaceAll(", $",")")+" VALUES (" 
                        +comaseperatedbindVarName.replaceAll(", $",");");
    }
}
