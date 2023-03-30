package com.functions.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

public class DbConnection {
    
    private AmazonDynamoDB client;
    private DynamoDB dynamoDB; 


    public DbConnection() {
        this.client = AmazonDynamoDBClientBuilder.standard().build();
        this.dynamoDB = new DynamoDB(client);
    }

    public PutItemResult insertRecord(Item item, String tableName){
        Table table = dynamoDB.getTable(tableName);
        return table.putItem(item).getPutItemResult();
    }

}
