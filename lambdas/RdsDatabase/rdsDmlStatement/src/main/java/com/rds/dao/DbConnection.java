package com.rds.dao;

import java.util.List;

import com.amazonaws.services.rdsdata.AWSRDSData;
import com.amazonaws.services.rdsdata.AWSRDSDataClient;
import com.amazonaws.services.rdsdata.model.BatchExecuteStatementRequest;
import com.amazonaws.services.rdsdata.model.BatchExecuteStatementResult;
import com.amazonaws.services.rdsdata.model.ExecuteStatementRequest;
import com.amazonaws.services.rdsdata.model.ExecuteStatementResult;
import com.amazonaws.services.rdsdata.model.SqlParameter;

public class DbConnection {
    private AWSRDSData client;
    private ExecuteStatementRequest request;
    private BatchExecuteStatementRequest batchRequest;

    private static final String databaseName= System.getenv("DBName");
    private static final String dbclusterArn= System.getenv("DBClusterArn");
    private static final String secreatArn= System.getenv("SecretArn");
    
    public DbConnection() {
        this.client = AWSRDSDataClient.builder().build();
        this.request = new ExecuteStatementRequest()
                            .withResourceArn(dbclusterArn)
                            .withSecretArn(secreatArn)
                            .withDatabase(databaseName)
                            .withIncludeResultMetadata(true);
        this.batchRequest = new BatchExecuteStatementRequest()
                            .withResourceArn(dbclusterArn)
                            .withSecretArn(secreatArn)
                            .withDatabase(databaseName);
    }

    public ExecuteStatementResult executeSqlStatement(String ddlStatement){
        return this.client.executeStatement(request.withSql(ddlStatement));
    }

    public BatchExecuteStatementResult executeSqlStatementWithParameterSet(String ddlStatement, List<List<SqlParameter>> parameterSet){
        return this.client.batchExecuteStatement(batchRequest.withSql(ddlStatement)
                                                .withParameterSets(parameterSet));
    }
}
