package com.rds.utils;

import java.util.List;
import java.util.Map;

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

    public List<JsonObject> getAllColumnRecord(String tableName, String offset, String Limit){
        return this.dbClient.executeSqlStatement("SELECT * FROM "+tableName+" LIMIT "+Limit+" OFFSET "+offset+";")
                .mapToList(JsonObject.class);
    }

    public Integer getTotalRecordCount(String tableName){
        return this.dbClient.executeSqlStatement("SELECT COUNT(*) FROM "+tableName+";")
                .singleValue(Integer.class);
    }

}
