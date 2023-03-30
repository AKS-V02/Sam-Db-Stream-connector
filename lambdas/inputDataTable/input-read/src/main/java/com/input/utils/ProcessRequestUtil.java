package com.input.utils;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.RangeKeyCondition;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.input.dao.DbConnection;

public class ProcessRequestUtil {
    
    private static final Map<String, String> headers = Map.of("Content-Type", "application/json", 
                                                             "Access-Control-Allow-Origin", "*", 
                                                             "Access-Control-Allow-Methods", "*");
    private static final String tableName= System.getenv("TABLE_NAME");
    private static final String partitionKey = System.getenv("PRIMARY_KEY");
    private static final String sortKey = System.getenv("SORT_KEY");
    private static final String lsiSortKey = System.getenv("LSI_KEY");
    private static final String lsiName = System.getenv("INDEX_NAME");

    private DbConnection dbConnection;

    public ProcessRequestUtil() {
        this.dbConnection = new DbConnection();
    }

    public static Map<String, String> getHeaders() {
        return headers;
    }

    public Item getRecordByPrimaryKey(String partitionkeyValue, String sortKeyValue){
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(partitionKey, partitionkeyValue, sortKey, sortKeyValue);
        return this.dbConnection.getRecord(spec, tableName);
    }


    public List<Item> getRecordsByPartitionkey(String partitionkeyValue, String lastPartitionkeyVal, String lastSortkeyVal){
        QuerySpec spec = new QuerySpec().withHashKey(partitionKey, partitionkeyValue).withMaxResultSize(3);
        if (!lastPartitionkeyVal.equalsIgnoreCase("") && !lastSortkeyVal.equalsIgnoreCase("")){
            spec.withExclusiveStartKey(partitionKey,lastPartitionkeyVal,sortKey,lastSortkeyVal);
        }  
        return this.dbConnection.quiryRecords(spec, tableName).firstPage().getLowLevelResult().getItems();
    }

    public List<Item> getRecordsByLsi(String partitionKeyValue, String lsiSortkeyValue, String lastPartitionkeyVal, String lastSortkeyVal){
        QuerySpec spec = new QuerySpec().withHashKey(partitionKey, partitionKeyValue)
                                        .withRangeKeyCondition(new RangeKeyCondition(lsiSortKey).eq(lsiSortkeyValue))                    
                                        .withMaxResultSize(3);
        if (!lastPartitionkeyVal.equalsIgnoreCase("") && !lastSortkeyVal.equalsIgnoreCase("")){
            spec.withExclusiveStartKey(partitionKey,lastPartitionkeyVal,lsiSortKey,lastSortkeyVal);
        }                                
        
        return this.dbConnection.quiryRecords(spec, tableName, lsiName).firstPage().getLowLevelResult().getItems();
    }

    
    public List<Item> scaneRecords(String lastPartitionkeyVal, String lastSortkeyVal){
        ScanSpec spec = new ScanSpec().withMaxResultSize(3);
        if (!lastPartitionkeyVal.equalsIgnoreCase("") && !lastSortkeyVal.equalsIgnoreCase("")){
            spec.withExclusiveStartKey(partitionKey,lastPartitionkeyVal,sortKey,lastSortkeyVal);
        }  
        return this.dbConnection.scanRecords(spec, tableName).firstPage().getLowLevelResult().getItems();
    }

}
