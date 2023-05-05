package com.rds.utils;

import java.util.Map;

import com.amazonaws.services.rdsdata.model.ExecuteStatementRequest;
import com.amazonaws.services.rdsdata.model.ExecuteStatementResult;
import com.amazonaws.services.rdsdata.model.RecordsFormatType;
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

    public String getAllColumnRecord(String tableName, String offset, String Limit){
        return this.dbClient.executeSqlStatement(request.withSql("SELECT * FROM "+tableName+" LIMIT "+Limit+" OFFSET "+offset+";")
                                                        .withFormatRecordsAs(RecordsFormatType.JSON)).getFormattedRecords();
    }

    public Long getTotalRecordCount(String tableName){
        ExecuteStatementResult result = this.dbClient.executeSqlStatement(request.withSql("SELECT COUNT(*) as totalCount FROM "+tableName+";"));
        return result.getRecords().get(0).get(0).getLongValue();
        // return result.getFormattedRecords();
    }

}
