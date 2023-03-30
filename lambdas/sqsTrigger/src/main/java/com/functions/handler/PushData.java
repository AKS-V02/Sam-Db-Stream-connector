package com.functions.handler;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.StreamsEventResponse;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.StreamRecord;
import com.functions.utils.ProcessRequestUtil;

public class PushData implements RequestHandler<DynamodbEvent, Serializable> {
    ProcessRequestUtil processRequestUtil = new ProcessRequestUtil();

    @Override
    public StreamsEventResponse handleRequest(DynamodbEvent input, Context context) {
        List<StreamsEventResponse.BatchItemFailure> batchItemFailures = new ArrayList<>();
        String curRecordSequenceNumber = "";
        for (DynamodbEvent.DynamodbStreamRecord dynamodbStreamRecord : input.getRecords()) {
          try {
                StreamRecord dynamodbRecord = dynamodbStreamRecord.getDynamodb();
                curRecordSequenceNumber = dynamodbRecord.getSequenceNumber();
                if(!dynamodbStreamRecord.getEventName().equalsIgnoreCase("REMOVE")){
                    String sqsMsgId = processRequestUtil.sendTransformedMsgToSqs(dynamodbRecord.getNewImage());
                    // String value = dynamodbStreamRecord.getEventSourceARN().split("/")[1];
                    context.getLogger().log(sqsMsgId);
                }        
            } catch (Exception e) {
                batchItemFailures.add(new StreamsEventResponse.BatchItemFailure(curRecordSequenceNumber));
                return new StreamsEventResponse(batchItemFailures);
            }
        }
       
       return new StreamsEventResponse();   
    }

}