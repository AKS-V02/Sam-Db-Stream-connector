package com.rds.utils;

import java.util.Map;

import com.amazon.rdsdata.client.ExecutionResult;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.rds.dao.DbConnection;


public class ExecuteStatement {
    private static final Map<String, String> headers = Map.of("Content-Type", "application/json", 
                                                             "Access-Control-Allow-Origin", "*", 
                                                             "Access-Control-Allow-Methods", "*");
    private DbConnection dbClient;
    public LambdaLogger logger;
    public ExecuteStatement() {
        this.dbClient = new DbConnection();
    } 

    public static Map<String, String> getHeaders() {
        return headers;
    }

    public LambdaLogger getLogger() {
        return logger;
    }

    public void setLogger(LambdaLogger logger) {
        this.logger = logger;
    }

    public ExecutionResult createTable(String tableName, JsonArray columnAsJsonArray){
       return this.dbClient.executeSqlStatement(getSqlCreateTableStatement(tableName, columnAsJsonArray));
    }

    public ExecutionResult describeTable(String tableName){
        return this.dbClient.executeSqlStatement("DESCRIBE "+tableName+";");
    }

    public ExecutionResult getAllTableName(){
        return this.dbClient.executeSqlStatement("SHOW TABLES;");
    }

    public ExecutionResult deleteTable(String tableName){
        return this.dbClient.executeSqlStatement("DROP TABLE "+tableName+";");
    }

    public ExecutionResult renameTable(String tableName, String newTableName){
        return this.dbClient.executeSqlStatement("ALTER TABLE "+tableName+" RENAME TO "+newTableName+";");
    }

    public ExecutionResult removeAllRecordsInTable(String tableName){
        return this.dbClient.executeSqlStatement("TRUNCATE TABLE "+tableName+";");
    }

    public ExecutionResult addColumnsToTable(String tableName, JsonArray columnAsJsonArray){
        return this.dbClient.executeSqlStatement(getSqlAddColumnsStatement(tableName, columnAsJsonArray));
    }

    public ExecutionResult deleteColumnsFromTable(String tableName, JsonArray columnAsJsonArray){
        return this.dbClient.executeSqlStatement(getSqlDeleteColumnsStatement(tableName, columnAsJsonArray));
    }

    public ExecutionResult renameColNameOfTable(String tableName, String oldColName, String newColName){
        return this.dbClient.executeSqlStatement("ALTER TABLE "+tableName+" RENAME COLUMN "+oldColName+" TO "+newColName+";");
    }

    private String getSqlCreateTableStatement(String tableName, JsonArray columnAsJsonArray){
        String statement = "CREATE TABLE IF NOT EXISTS "+tableName+" (";
        for(JsonElement columnObj:columnAsJsonArray){
            statement = statement + columnObj.getAsJsonObject().get("colName").getAsString() +" "+
                                    columnObj.getAsJsonObject().get("valType").getAsString()+", ";
        } 
        this.logger.log(statement.replaceAll(", $",");"));
        return statement.replaceAll(", $",");");
    }

    private String getSqlAddColumnsStatement(String tableName, JsonArray columnAsJsonArray){
        String statement = "ALTER TABLE "+tableName+" ADD (";
        for(JsonElement columnObj:columnAsJsonArray){
            statement = statement + columnObj.getAsJsonObject().get("colName").getAsString() +" "+
                                    columnObj.getAsJsonObject().get("valType").getAsString()+", ";
        } 
        return statement.replaceAll(", $",");");
    }

    private String getSqlDeleteColumnsStatement(String tableName, JsonArray columnAsJsonArray) {
        String statement = "ALTER TABLE "+tableName+" ";
        for(JsonElement columnObj:columnAsJsonArray){
            statement = statement + "DROP COLUMN "+columnObj.getAsString()+", ";
        } 
        return statement.replaceAll(", $",";");
    }


}
