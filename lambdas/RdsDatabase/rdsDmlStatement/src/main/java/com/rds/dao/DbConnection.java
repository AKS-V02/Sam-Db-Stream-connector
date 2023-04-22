package com.rds.dao;

import java.util.List;

import com.amazon.rdsdata.client.ExecutionResult;
import com.amazon.rdsdata.client.RdsData;
import com.google.gson.JsonObject;

public class DbConnection {
    private RdsData client;
    private static final String databaseName= System.getenv("DBName");
    private static final String dbclusterArn= System.getenv("DBClusterArn");
    private static final String secreatArn= System.getenv("SecretArn");

    public static String getDatabasename() {
        return databaseName;
    }

    public static String getDbclusterarn() {
        return dbclusterArn;
    }

    public static String getSecreatarn() {
        return secreatArn;
    }

    public DbConnection() {
        this.client = RdsData.builder()
                    .database(databaseName)
                    .resourceArn(dbclusterArn)
                    .secretArn(secreatArn).build();;
    }

    public ExecutionResult executeSqlStatement(String ddlStatement){
        return this.client.forSql(ddlStatement)
                        .execute();
    }

    public ExecutionResult executeSqlStatementWithParameterSet(String ddlStatement, List<JsonObject> parameterSet){
        return this.client.forSql(ddlStatement)
                        .withParamSets(parameterSet)
                        .execute();
    }
}
