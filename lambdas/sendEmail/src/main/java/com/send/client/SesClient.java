package com.send.client;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityResult;

public class SesClient {
    private AmazonSimpleEmailService client;

    public SesClient() {
        this.client = AmazonSimpleEmailServiceClientBuilder.standard()
                        .withRegion(Regions.AP_SOUTH_1).build();
    }

    public SendTemplatedEmailResult sendEmail(SendTemplatedEmailRequest request){
        return client.sendTemplatedEmail(request);
    }
    
    public VerifyEmailIdentityResult varifyEmail(VerifyEmailIdentityRequest request){
        return client.verifyEmailIdentity(request);
    }
}
