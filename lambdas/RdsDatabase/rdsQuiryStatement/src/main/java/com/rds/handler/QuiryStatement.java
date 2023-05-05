package com.rds.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rds.utils.ExecuteStatement;

public class QuiryStatement implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent>{
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    ExecuteStatement executeStatement = new ExecuteStatement();
    // Gson gson = new Gson();
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            JsonObject inputObj = JsonParser.parseString(input.getBody()).getAsJsonObject(); 
            if(input.getPath().contains("/records")){
                String result = executeStatement.getAllColumnRecord(inputObj.get("tableName").getAsString()
                                                                        , inputObj.get("offset").getAsString(), 
                                                                        inputObj.get("limit").getAsString());
                return response.withStatusCode(200).withBody(result).withHeaders(ExecuteStatement.getHeaders()); 
            } else if(input.getPath().contains("/total-count")){
                Long result = executeStatement.getTotalRecordCount(inputObj.get("tableName").getAsString());
                return response.withStatusCode(200).withBody(result.toString()).withHeaders(ExecuteStatement.getHeaders()); 
            }
            return response.withStatusCode(404).withBody("Wrong Path").withHeaders(ExecuteStatement.getHeaders());
        } catch (Exception e) {
            return response.withStatusCode(400).withBody(e.getMessage()).withHeaders(ExecuteStatement.getHeaders());
        }
    }
    
}
