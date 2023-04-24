package com.rds.utils;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.rdsdata.model.ExecuteStatementRequest;
import com.amazonaws.services.rdsdata.model.ExecuteStatementResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.rds.dao.DbConnection;


public class ExecuteStatement {
    private static final Map<String, String> headers = Map.of("Content-Type", "application/json", 
                                                             "Access-Control-Allow-Origin", "*", 
                                                             "Access-Control-Allow-Methods", "*");
    private static final String databaseName= System.getenv("DBName");
    private static final String dbclusterArn= System.getenv("DBClusterArn");
    private static final String secreatArn= System.getenv("SecretArn");


    private DbConnection dbClient;
    private ExecuteStatementRequest request;
    public LambdaLogger logger;

    public ExecuteStatement() {
        this.dbClient = new DbConnection();
        this.request = new ExecuteStatementRequest()
                            .withResourceArn(dbclusterArn)
                            .withSecretArn(secreatArn)
                            .withDatabase(databaseName)
                            .withIncludeResultMetadata(true);
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

    public ExecuteStatementResult createTable(String tableName, JsonArray columnAsJsonArray){
       return this.dbClient.executeSqlStatement(request.withSql(getSqlCreateTableStatement(tableName, columnAsJsonArray)));
    }

    public ExecuteStatementResult describeTable(String tableName){
        return this.dbClient.executeSqlStatement(request.withSql("DESCRIBE "+tableName+";"));
    }

    public ExecuteStatementResult getAllTableName(){
        return this.dbClient.executeSqlStatement(request.withSql("SHOW TABLES;"));
    }

    public ExecuteStatementResult deleteTable(String tableName){
        return this.dbClient.executeSqlStatement(request.withSql("DROP TABLE "+tableName+";"));
    }

    public ExecuteStatementResult renameTable(String tableName, String newTableName){
        return this.dbClient.executeSqlStatement(request.withSql("ALTER TABLE "+tableName+" RENAME TO "+newTableName+";"));
    }

    public ExecuteStatementResult removeAllRecordsInTable(String tableName){
        return this.dbClient.executeSqlStatement(request.withSql("TRUNCATE TABLE "+tableName+";"));
    }

    public ExecuteStatementResult addColumnsToTable(String tableName, JsonArray columnAsJsonArray){
        return this.dbClient.executeSqlStatement(request.withSql(getSqlAddColumnsStatement(tableName, columnAsJsonArray)));
    }

    public ExecuteStatementResult deleteColumnsFromTable(String tableName, JsonArray columnAsJsonArray){
        return this.dbClient.executeSqlStatement(request.withSql(getSqlDeleteColumnsStatement(tableName, columnAsJsonArray)));
    }

    public ExecuteStatementResult renameColNameOfTable(String tableName, String oldColName, String newColName){
        return this.dbClient.executeSqlStatement(request.withSql("ALTER TABLE "+tableName+" RENAME COLUMN "+oldColName+" TO "+newColName+";"));
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
