package com.rds.handler;

import com.amazon.rdsdata.client.ExecutionResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rds.utils.ExecuteStatement;

public class DmlStatement implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent>{

    private APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    ExecuteStatement executeStatement = new ExecuteStatement();
    Gson gson = new Gson();
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            JsonObject inputObj = JsonParser.parseString(input.getBody()).getAsJsonObject(); 
            if(input.getPath().contains("/add-records")){
                ExecutionResult result = executeStatement.addRecordsIntoTable(inputObj.get("tableName").getAsString(),
                                                     inputObj.get("columnsNameList").getAsJsonArray(), 
                                                     inputObj.get("columnsvalueList").getAsJsonArray());
                return response.withStatusCode(200).withBody(gson.toJson(result)).withHeaders(ExecuteStatement.getHeaders()); 
            }
            return response.withStatusCode(404).withBody("Wrong Path").withHeaders(ExecuteStatement.getHeaders());
        } catch (Exception e) {
            return response.withStatusCode(400).withBody(e.getMessage()).withHeaders(ExecuteStatement.getHeaders());
        }
    }
    
}
