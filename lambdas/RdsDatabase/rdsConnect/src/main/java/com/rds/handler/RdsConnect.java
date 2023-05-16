package com.rds.handler;

import java.sql.ResultSet;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rds.utils.ExecuteStatement;

public class RdsConnect implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent>{
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    ExecuteStatement executeStatement = new ExecuteStatement();
    Gson gson = new Gson();
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        
        try {
            JsonObject inputObj = JsonParser.parseString(input.getBody()).getAsJsonObject();
            String action = input.getPathParameters().get("proxy"); 
            executeStatement.startConnection();
            if(action.equalsIgnoreCase("/create-table")){
                int result = executeStatement.createTable(inputObj.get("tableName").getAsString(), 
                                                                    inputObj.get("columnsList").getAsJsonArray());
                executeStatement.closeConnection();
                return response.withStatusCode(200).withBody("Table Created result"+result).withHeaders(ExecuteStatement.getHeaders()); 
            } else if (action.equalsIgnoreCase("/add-records")){
                 int result = executeStatement.addRecordsIntoTable(inputObj.get("tableName").getAsString(),
                                                     inputObj.get("columnsNameList").getAsJsonArray(), 
                                                     inputObj.get("columnsvalueList").getAsJsonArray());
                executeStatement.closeConnection();
                return response.withStatusCode(200).withBody("Recordes added sucessfully with result "+result).withHeaders(ExecuteStatement.getHeaders()); 
            }else if(action.equalsIgnoreCase("/update-records")){
                 int result = executeStatement.updateRecordOfTable(inputObj.get("tableName").getAsString(),
                                                     inputObj.get("conditionColumnValue").getAsJsonObject(), 
                                                     inputObj.get("updateColumnValue").getAsJsonObject());
                executeStatement.closeConnection();
                return response.withStatusCode(200).withBody("Records Updated With Result"+result).withHeaders(ExecuteStatement.getHeaders()); 
            }else if(action.equalsIgnoreCase("/get-records")){
                ResultSet result = executeStatement.getAllColumnRecord(inputObj.get("tableName").getAsString()
                                                                        , inputObj.get("offset").getAsString(), 
                                                                        inputObj.get("limit").getAsString());
                executeStatement.closeConnection();
                return response.withStatusCode(200).withBody(gson.toJson(result)).withHeaders(ExecuteStatement.getHeaders()); 
            } else if(action.equalsIgnoreCase("/get-total-count")){
                ResultSet result = executeStatement.getTotalRecordCount(inputObj.get("tableName").getAsString());
                executeStatement.closeConnection();
                return response.withStatusCode(200).withBody(gson.toJson(result)).withHeaders(ExecuteStatement.getHeaders()); 
            }
            executeStatement.closeConnection();
            return response.withStatusCode(404).withBody("Wrong Path").withHeaders(ExecuteStatement.getHeaders()); 
        } catch (Exception e) {
            executeStatement.closeConnection();
            return response.withStatusCode(400).withBody(e.getMessage()).withHeaders(ExecuteStatement.getHeaders());
        }
        
    }
    
}
