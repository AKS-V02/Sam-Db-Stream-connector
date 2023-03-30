package com.functions.utils;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.functions.dao.DbConnection;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ProcessRequestUtil {
    
    private static final String tableName= System.getenv("TABLE_NAME");
    private static final String primaryKey = System.getenv("PRIMARY_KEY");
    private static final String sortKey = System.getenv("SORT_KEY");
    private static final String lsiKey = System.getenv("LSI_KEY");

    private DbConnection dbConnection;



    public ProcessRequestUtil() {
        this.dbConnection = new DbConnection();
    }

    public PutItemResult saveSingleRecord(String body){

        JsonObject jsonObj = JsonParser.parseString(body).getAsJsonObject();

        return dbConnection.insertRecord(getRecordItem(jsonObj), tableName);
    }


    private Item getRecordItem(JsonObject jsonObject){
        
        Item item = new Item().withPrimaryKey(primaryKey, jsonObject.get(primaryKey).getAsString(),
                                                sortKey, jsonObject.get(sortKey).getAsString())
                                            .withString(lsiKey, jsonObject.get(lsiKey).getAsString())
                                            .withString("from_source", jsonObject.get("from_source").getAsString())
                                            .withString("to_source", jsonObject.get("to_source").getAsString());

        return item;
    }


    // private String getRandomString(){
    //     return UUID.randomUUID().toString();
    // }

}
