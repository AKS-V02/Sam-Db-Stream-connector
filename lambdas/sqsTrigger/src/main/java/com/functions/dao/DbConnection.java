package com.functions.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

public class DbConnection {
    
    private AmazonDynamoDB client;
    private DynamoDB dynamoDB; 


    public DbConnection() {
        this.client = AmazonDynamoDBClientBuilder.standard().build();
        this.dynamoDB = new DynamoDB(client);
    }

    public Item getRecord(GetItemSpec spec, String tableName){
        Table table = dynamoDB.getTable(tableName);
        return table.getItem(spec);
    }

    // public ItemCollection<QueryOutcome> quiryRecords(QuerySpec spec, String tableName, String indexname){
    //     Table table = dynamoDB.getTable(tableName);
    //     Index index = table.getIndex(indexname);
    //     return index.query(spec);
    // }

}
