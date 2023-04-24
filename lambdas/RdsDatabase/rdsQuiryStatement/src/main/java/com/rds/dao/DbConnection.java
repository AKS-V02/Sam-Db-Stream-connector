package com.rds.dao;

import com.amazonaws.services.rdsdata.AWSRDSData;
import com.amazonaws.services.rdsdata.AWSRDSDataClient;
import com.amazonaws.services.rdsdata.model.ExecuteStatementRequest;
import com.amazonaws.services.rdsdata.model.ExecuteStatementResult;

public class DbConnection {
    private AWSRDSData client;
    
    public DbConnection() {
        this.client = AWSRDSDataClient.builder().build();
    }

    public ExecuteStatementResult executeSqlStatement(ExecuteStatementRequest ddlStatement){
        return this.client.executeStatement(ddlStatement);
    }
}
