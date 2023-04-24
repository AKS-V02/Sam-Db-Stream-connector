package com.rds.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.rdsdata.model.BatchExecuteStatementResult;
import com.amazonaws.services.rdsdata.model.Field;
import com.amazonaws.services.rdsdata.model.SqlParameter;
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

    public BatchExecuteStatementResult addRecordsIntoTable(String tableName, JsonArray columnNameAsJsonArray, JsonArray columnValueAsJsonArray){
        
       return this.dbClient.executeSqlStatementWithParameterSet(getAddRecordSqlStatement(tableName, columnNameAsJsonArray), 
                                                        getSqlParameterSet(columnNameAsJsonArray, columnValueAsJsonArray));
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

    private List<List<SqlParameter>> getSqlParameterSet( JsonArray columnNameAsJsonArray, JsonArray columnValueAsJsonArray){
        List<List<SqlParameter>> parameterSet = new ArrayList<>();
        for(JsonElement valueObj:columnValueAsJsonArray){
            List<SqlParameter> record = new ArrayList<>();
            for(JsonElement colname :columnNameAsJsonArray){
                if(valueObj.getAsJsonObject().get(colname.getAsString()) == null){
                    record.add(new SqlParameter()
                            .withName(colname.getAsString())
                            .withValue(new Field().withIsNull(true)));
                } else if(valueObj.getAsJsonObject().get(colname.getAsString()).getAsJsonPrimitive().isNumber()){
                    record.add(new SqlParameter()
                            .withName(colname.getAsString())
                            .withValue(new Field()
                                    .withLongValue(valueObj
                                    .getAsJsonObject()
                                    .get(colname.getAsString())
                                    .getAsLong())));
                } else {
                    record.add(new SqlParameter()
                            .withName(colname.getAsString())
                            .withValue(new Field()
                                    .withStringValue(valueObj
                                    .getAsJsonObject()
                                    .get(colname.getAsString())
                                    .getAsString())));
                }
            }
            parameterSet.add(record);
        }
        return parameterSet;
    }
}
