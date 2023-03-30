package com.functions.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.functions.dao.DbConnection;
import com.functions.dao.SQSconnection;

public class ProcessRequestUtil {
    
    private static final String tableName= System.getenv("TABLE_NAME");
    private static final String partitionKey = System.getenv("PRIMARY_KEY");
    private static final String sortKey = System.getenv("SORT_KEY");
    private static final String queUrl= System.getenv("QUEUE_URL");
    // private static final String lsiSortKey = System.getenv("LSI_KEY");
    // private static final String lsiName = System.getenv("INDEX_NAME");

    private DbConnection dbConnection;
    private SQSconnection sqsConnection;

    public ProcessRequestUtil() {
        this.dbConnection = new DbConnection();
        this.sqsConnection = new SQSconnection();
    }

    private Item getRecordByPrimaryKey(String partitionkeyValue, String sortKeyValue){
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(partitionKey, partitionkeyValue, sortKey, sortKeyValue);
        return this.dbConnection.getRecord(spec, tableName);
    }

    public String sendTransformedMsgToSqs(Map<String, AttributeValue> newImage){
        Item joltTableItem = getRecordByPrimaryKey(newImage.get("from_source").getS(), newImage.get("to_source").getS());
        String inputJson = getInputJsonSting(newImage);
        String joltSpec = joltTableItem.getString("JoltSpec");
        return this.sqsConnection.sendSQSMessage(joltTransform(inputJson, joltSpec), queUrl);
    }

    
    private String joltTransform(String inputJson, String joltSpec){
        List<Object> specs = JsonUtils.jsonToList(joltSpec);
        Chainr chainr = Chainr.fromSpec(specs);
        // String json = JsonUtils.toJsonString(inputdata);
        Object inputJSON = JsonUtils.jsonToObject(inputJson);
        Object transformedOutput = chainr.transform(inputJSON);
        return JsonUtils.toJsonString(transformedOutput);
    }
    
    private String getInputJsonSting(Map<String, AttributeValue> newImage){
        Map<String, String> inputObj = new HashMap<>();
        for(String key: newImage.keySet()){
            inputObj.put(key, newImage.get(key).getS());
        }
        return JsonUtils.toJsonString(inputObj);
    }

    // public ItemCollection<QueryOutcome> getRecordsByLsi(String partitionKeyValue, String lsiSortkeyValue, String lastPartitionkeyVal, String lastSortkeyVal){
    //     QuerySpec spec = new QuerySpec().withHashKey(partitionKey, partitionKeyValue)
    //                                     .withRangeKeyCondition(new RangeKeyCondition(lsiSortKey).eq(lsiSortkeyValue))                    
    //                                     .withMaxResultSize(3);
    //     if (!lastPartitionkeyVal.equalsIgnoreCase("") && !lastSortkeyVal.equalsIgnoreCase("")){
    //         spec.withExclusiveStartKey(partitionKey,lastPartitionkeyVal,lsiSortKey,lastSortkeyVal);
    //     }                                
        
    //     return this.dbConnection.quiryRecords(spec, tableName, lsiName);
    // }


}
