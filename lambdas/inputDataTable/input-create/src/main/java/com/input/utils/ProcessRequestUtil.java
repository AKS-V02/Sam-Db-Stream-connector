package com.input.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.input.dao.DbConnection;

public class ProcessRequestUtil {
    
    private static final Map<String, String> headers = Map.of("Content-Type", "application/json", 
                                                             "Access-Control-Allow-Origin", "*", 
                                                             "Access-Control-Allow-Methods", "*");
    private static final String tableName= System.getenv("TABLE_NAME");
    private static final String primaryKey = System.getenv("PRIMARY_KEY");
    private static final String sortKey = System.getenv("SORT_KEY");
    private static final String lsiKey = System.getenv("LSI_KEY");

    private DbConnection dbConnection;



    public ProcessRequestUtil() {
        this.dbConnection = new DbConnection();
    }

    public static Map<String, String> getHeaders() {
        return headers;
    }

    public PutItemResult saveSingleRecord(String body){

        JsonObject jsonObj = JsonParser.parseString(body).getAsJsonObject();

        return dbConnection.insertRecord(getRecordItem(jsonObj), tableName);
    }

    public Map<String, List<WriteRequest>> saveMultipleRecord(String body){

        JsonObject jsonObj = JsonParser.parseString(body).getAsJsonObject();
        return  dbConnection.insertRecords(getRecordItems(jsonObj), tableName);
    }


    private List<Item> getRecordItems(JsonObject jsonObject){
        List<Item> itemList = new ArrayList<>();
        for(JsonElement obj : jsonObject.getAsJsonArray()){           
            itemList.add(getRecordItem(obj.getAsJsonObject()));
        }
        return itemList;
    }

    private Item getRecordItem(JsonObject jsonObject){
        
        Item item = new Item().withPrimaryKey(primaryKey, jsonObject.get(primaryKey).getAsString(),
                                                sortKey, getRandomString())
                                            .withString(lsiKey, jsonObject.get(lsiKey).getAsString())
                                            .withString("last_name", jsonObject.get("last_name").getAsString())
                                            .withString("from_source", jsonObject.get("from_source").getAsString())
                                            .withString("to_source", jsonObject.get("to_source").getAsString());

        return item;
    }


    private String getRandomString(){
        return UUID.randomUUID().toString();
    }

}
