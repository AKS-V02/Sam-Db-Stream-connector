package com.send.handler;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.send.utils.ProcessRequest;

public class SendEmail implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {
    private APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    private ProcessRequest processRequest = new ProcessRequest();
    private Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        processRequest.setLogger(context.getLogger());
        try {
            JsonObject inputBody = JsonParser.parseString(input.getBody()).getAsJsonObject();  
            Map<String,String> queryParameter = input.getQueryStringParameters();
            if(queryParameter!=null && queryParameter.get("varifyEmail").equalsIgnoreCase("Y")){
                return response.withBody(gson.toJson(processRequest.varifyEmailIdentity(inputBody.get("emailId").getAsString())))
                    .withHeaders(ProcessRequest.getHeaders()).withStatusCode(200);
            } else {  
                String tomail = inputBody.get("toMail").getAsString();
                String fromMail = inputBody.get("fromMail").getAsString();
                String templateMetaData = inputBody.get("templateMetaData").getAsString();
                return response.withBody(gson.toJson(processRequest.sendEmailWithTemplate(fromMail, tomail, templateMetaData)))
                    .withHeaders(ProcessRequest.getHeaders()).withStatusCode(200);
            }
        } catch (Exception e) {
            return response.withBody("Exception "+e)
                    .withHeaders(ProcessRequest.getHeaders()).withStatusCode(500);
        }
    }
    
}
