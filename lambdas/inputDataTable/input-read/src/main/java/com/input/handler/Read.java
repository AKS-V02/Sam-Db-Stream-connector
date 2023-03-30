package com.input.handler;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.input.utils.ProcessRequestUtil;


public class Read implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent>{
    Gson gson = new Gson();
    ProcessRequestUtil processRequestUtil = new ProcessRequestUtil();
    String primaryKey = System.getenv("PRIMARY_KEY");
    String sortKey = System.getenv("SORT_KEY");
    String lsiKey = System.getenv("LSI_KEY");
    APIGatewayProxyResponseEvent result = new APIGatewayProxyResponseEvent();
    String lastPartitionkeyVal = "";
    String lastSortkeyVal = "";
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            Map<String,String> queryParameter = input.getQueryStringParameters();
            lastPartitionkeyVal="";
            lastSortkeyVal = "";
            if(queryParameter!=null && queryParameter.keySet().contains(primaryKey)){
                if(queryParameter.keySet().contains(sortKey)){
                    return result.withStatusCode(200)
                    .withBody(gson.toJson(processRequestUtil.getRecordByPrimaryKey(queryParameter.get(primaryKey), queryParameter.get(sortKey))))
                    .withHeaders(ProcessRequestUtil.getHeaders());
                }else if(queryParameter.keySet().contains(lsiKey)) {
                    if(queryParameter.containsKey("last"+primaryKey) && queryParameter.containsKey("last"+sortKey)){
                        lastPartitionkeyVal=queryParameter.get("last"+primaryKey);
                        lastSortkeyVal = queryParameter.get("last"+sortKey);
                    }
                    return result.withStatusCode(200)
                    .withBody(gson.toJson(processRequestUtil.getRecordsByLsi(queryParameter.get(primaryKey), queryParameter.get(lsiKey), lastPartitionkeyVal, lastSortkeyVal)))
                    .withHeaders(ProcessRequestUtil.getHeaders());
                } else {
                    if(queryParameter.containsKey("last"+primaryKey) && queryParameter.containsKey("last"+sortKey)){
                        lastPartitionkeyVal=queryParameter.get("last"+primaryKey);
                        lastSortkeyVal = queryParameter.get("last"+sortKey);
                    }
                    return result.withStatusCode(200)
                    .withBody(gson.toJson(processRequestUtil.getRecordsByPartitionkey(queryParameter.get(primaryKey), lastPartitionkeyVal, lastSortkeyVal)))
                    .withHeaders(ProcessRequestUtil.getHeaders());
                }
            } else {
                if(queryParameter!=null && queryParameter.containsKey("last"+primaryKey) && queryParameter.containsKey("last"+sortKey)){
                    lastPartitionkeyVal=queryParameter.get("last"+primaryKey);
                    lastSortkeyVal = queryParameter.get("last"+sortKey);
                }
                return result.withStatusCode(200).withBody(gson.toJson(processRequestUtil.scaneRecords(lastPartitionkeyVal, lastSortkeyVal))).withHeaders(ProcessRequestUtil.getHeaders());
            }
           
        } catch (Exception e) {
            return result.withStatusCode(400).withBody(e.getMessage()).withHeaders(ProcessRequestUtil.getHeaders());
        }
    }
}
