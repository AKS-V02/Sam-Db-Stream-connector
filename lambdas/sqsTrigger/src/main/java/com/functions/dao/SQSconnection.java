package com.functions.dao;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SQSconnection {
    private AmazonSQS sqs;

    public SQSconnection() {
        this.sqs = AmazonSQSClientBuilder.defaultClient();;
    }
    
    public String sendSQSMessage(String body, String queueUrl) {
        return sqs.sendMessage(new SendMessageRequest()
                                .withDelaySeconds(10)
                                .withMessageBody(body)
                                .withQueueUrl(queueUrl))
                                .getMessageId();
    }

}
