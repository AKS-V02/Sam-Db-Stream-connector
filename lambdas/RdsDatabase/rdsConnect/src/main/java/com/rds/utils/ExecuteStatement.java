package com.rds.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rds.dao.DbConnection;



public class ExecuteStatement {
    private static final Map<String, String> headers = Map.of("Content-Type", "application/json", 
                                                             "Access-Control-Allow-Origin", "*", 
                                                             "Access-Control-Allow-Methods", "*");
    
    public LambdaLogger LOGGER;
    
    private Connection conn = null;

    
    public ExecuteStatement() {
    }

    public void setLogger(LambdaLogger LOGGER){
        this.LOGGER = LOGGER;

    }

    public static Map<String, String> getHeaders() {
        return headers;
    }

    public void startConnection() throws SQLException{
        conn = DbConnection.getConnection(LOGGER);
    }

    public void closeConnection(){
        DbConnection.closeConnection(conn, LOGGER);
    }

    public int createTable(String tableName, JsonArray columnAsJsonArray) throws Exception{
        LOGGER.log("createTable tableName"+tableName+" columnAsJsonArray "+columnAsJsonArray.toString());
        return DbConnection.executeUpdate(conn, getSqlCreateTableStatement(tableName, columnAsJsonArray), LOGGER);
    }

    // public ExecuteStatementResult createTable(String tableName, JsonArray columnAsJsonArray){
    //    return this.dbClient.executeSqlStatement(request.withSql(getSqlCreateTableStatement(tableName, columnAsJsonArray)));
    // }

    // public ExecuteStatementResult describeTable(String tableName){
    //     return this.dbClient.executeSqlStatement(request.withSql("DESCRIBE "+tableName+";"));
    // }

    // public ExecuteStatementResult getAllTableName(){
    //     return this.dbClient.executeSqlStatement(request.withSql("SHOW TABLES;"));
    // }

    // public ExecuteStatementResult deleteTable(String tableName){
    //     return this.dbClient.executeSqlStatement(request.withSql("DROP TABLE "+tableName+";"));
    // }

    // public ExecuteStatementResult renameTable(String tableName, String newTableName){
    //     return this.dbClient.executeSqlStatement(request.withSql("ALTER TABLE "+tableName+" RENAME TO "+newTableName+";"));
    // }

    // public ExecuteStatementResult removeAllRecordsInTable(String tableName){
    //     return this.dbClient.executeSqlStatement(request.withSql("TRUNCATE TABLE "+tableName+";"));
    // }

    // public ExecuteStatementResult addColumnsToTable(String tableName, JsonArray columnAsJsonArray){
    //     return this.dbClient.executeSqlStatement(request.withSql(getSqlAddColumnsStatement(tableName, columnAsJsonArray)));
    // }

    // public ExecuteStatementResult deleteColumnsFromTable(String tableName, JsonArray columnAsJsonArray){
    //     return this.dbClient.executeSqlStatement(request.withSql(getSqlDeleteColumnsStatement(tableName, columnAsJsonArray)));
    // }

    // public ExecuteStatementResult renameColNameOfTable(String tableName, String oldColName, String newColName){
    //     return this.dbClient.executeSqlStatement(request.withSql("ALTER TABLE "+tableName+" RENAME COLUMN "+oldColName+" TO "+newColName+";"));
    // }

    private String getSqlCreateTableStatement(String tableName, JsonArray columnAsJsonArray){
        String statement = "CREATE TABLE IF NOT EXISTS "+tableName+" (";
        for(JsonElement columnObj:columnAsJsonArray){
            statement = statement + columnObj.getAsJsonObject().get("colName").getAsString() +" "+
                                    columnObj.getAsJsonObject().get("valType").getAsString()+", ";
        } 
        return statement.replaceAll(", $",");");
    }

    // private String getSqlAddColumnsStatement(String tableName, JsonArray columnAsJsonArray){
    //     String statement = "ALTER TABLE "+tableName+" ADD (";
    //     for(JsonElement columnObj:columnAsJsonArray){
    //         statement = statement + columnObj.getAsJsonObject().get("colName").getAsString() +" "+
    //                                 columnObj.getAsJsonObject().get("valType").getAsString()+", ";
    //     } 
    //     return statement.replaceAll(", $",");");
    // }

    // private String getSqlDeleteColumnsStatement(String tableName, JsonArray columnAsJsonArray) {
    //     String statement = "ALTER TABLE "+tableName+" ";
    //     for(JsonElement columnObj:columnAsJsonArray){
    //         statement = statement + "DROP COLUMN "+columnObj.getAsString()+", ";
    //     } 
    //     return statement.replaceAll(", $",";");
    // }




    // Dml Statement Started
    public int addRecordsIntoTable(String tableName, JsonArray columnNameAsJsonArray, JsonArray columnValueAsJsonArray) throws Exception{
        LOGGER.log("createTable tableName"+tableName
                    +" columnNameAsJsonArray "+columnNameAsJsonArray.toString()
                    +" columnValueAsJsonArray " + columnValueAsJsonArray.toString());
        return DbConnection.executeUpdate(conn,getAddRecordSqlStatement(tableName, columnNameAsJsonArray, columnValueAsJsonArray), LOGGER);
     }
     
     public int updateRecordOfTable(String tableName, JsonObject conditionColumnValue, JsonObject updateColumnValue) throws Exception{
            LOGGER.log("createTable tableName"+tableName
                        +" conditionColumnValue "+conditionColumnValue.toString()
                        +" updateColumnValue " + updateColumnValue.toString());
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
             return DbConnection.executeUpdate(conn,statement.toString(), LOGGER);
           }

     private String getAddRecordSqlStatement(String tableName, JsonArray columnNameAsJsonArray, JsonArray columnValueAsJsonArray) {
         StringBuilder statement=new StringBuilder("INSERT INTO ").append(tableName).append(" (");
         for(JsonElement columnObj:columnNameAsJsonArray){
             statement.append(columnObj.getAsString()).append(", ");
         } 
         statement.replace(statement.length()-2,statement.length(),")")
                 .append(" VALUES ")
                 .append(getSqlParameterSet(columnNameAsJsonArray, columnValueAsJsonArray));
         return statement.toString();
     }
 
     private String getSqlParameterSet( JsonArray columnNameAsJsonArray, JsonArray columnValueAsJsonArray){
         StringBuilder parameterSet=new StringBuilder();
         for(JsonElement valueObj:columnValueAsJsonArray){
             parameterSet.append("(");
             for(JsonElement colname :columnNameAsJsonArray){
 
                 parameterSet.append(convertValueForSqlStatement(valueObj.getAsJsonObject().get(colname.getAsString()))).append(", ");
             }
             parameterSet.replace(parameterSet.length()-2,parameterSet.length(),"), ");
         }
         return parameterSet.replace(parameterSet.length()-2,parameterSet.length(),";").toString();
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




    // Quiry code starts 

    public List<Map<String, Object>> getAllColumnRecord(String tableName, String offset, String Limit) throws Exception{
        LOGGER.log("tableName "+tableName+" offset "+offset+" Limit "+Limit);
        return DbConnection.executeQuiry(conn,"SELECT * FROM "+tableName+" LIMIT "+Limit+" OFFSET "+offset+";", LOGGER);
    }

    public List<Map<String, Object>> getTotalRecordCount(String tableName) throws Exception{
        LOGGER.log("tableName "+tableName);
        return DbConnection.executeQuiry(conn, "SELECT COUNT(*) as totalCount FROM "+tableName+";", LOGGER);
    }

}
