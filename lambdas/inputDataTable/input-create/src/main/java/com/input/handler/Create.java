package com.input.handler;


import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.input.utils.ProcessRequestUtil;

public class Create implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent>{
    Gson gson = new Gson();
    ProcessRequestUtil processRequestUtil = new ProcessRequestUtil();
    APIGatewayProxyResponseEvent result = new APIGatewayProxyResponseEvent();
        
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        LambdaLogger logger = context.getLogger();
        try {
            String body = input.getBody();
            logger.log("Body is:"+body);
            Map<String,String> queryParameter = input.getQueryStringParameters();
            if(queryParameter!=null && queryParameter.get("isList").equalsIgnoreCase("Y")){
                return result.withStatusCode(200).withBody(gson.toJson(processRequestUtil.saveMultipleRecord(body))).withHeaders(ProcessRequestUtil.getHeaders());
            } else {
                return result.withStatusCode(200).withBody(gson.toJson(processRequestUtil.saveSingleRecord(body))).withHeaders(ProcessRequestUtil.getHeaders());
            }
        } catch (Exception e) {
            return result.withStatusCode(400).withBody("Error "+e.getMessage()).withHeaders(ProcessRequestUtil.getHeaders());
        }
    }
}
