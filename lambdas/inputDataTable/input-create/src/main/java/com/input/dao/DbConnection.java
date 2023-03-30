package com.input.dao;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

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

    public Map<String, List<WriteRequest>> insertRecords(List<Item> items, String tableName){
        TableWriteItems writeItems = new TableWriteItems(tableName).
                withItemsToPut(items);
        BatchWriteItemOutcome response = dynamoDB.batchWriteItem(writeItems);
        return response.getUnprocessedItems();
    }

}
