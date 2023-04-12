package com.send.utils;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityResult;
import com.send.client.SesClient;

public class ProcessRequest {
    private static final Map<String, String> headers = Map.of("Content-Type", "application/json", 
                                                            "Access-Control-Allow-Origin", "*", 
                                                            "Access-Control-Allow-Methods", "*");
    private static final String templateName= System.getenv("Template");
    private static final String configsetName= System.getenv("ConfigsetName");

    private SesClient client;
    private LambdaLogger logger;

    public ProcessRequest() {
        this.client = new SesClient();
    }

    public void setLogger(LambdaLogger logger) {
        this.logger = logger;
    }

    public static Map<String, String> getHeaders() {
        return headers;
    }

    public SendTemplatedEmailResult sendEmailWithTemplate(String fromMail, String tomail, String templateMetadata){
        return this.client.sendEmail(getTemplatedEmailReq(fromMail, tomail, templateMetadata));
    }

    public VerifyEmailIdentityResult varifyEmailIdentity(String emailAddress){
        this.logger.log("emailAddress "+emailAddress);
        return this.client.varifyEmail(getVarifyEmailReq(emailAddress));
    }


    private SendTemplatedEmailRequest getTemplatedEmailReq(String fromMail, String tomail, String templateMetadata){
        this.logger.log("fromMail "+fromMail+" tomail "+tomail+" templateMetadata "+templateMetadata);
        this.logger.log(" templateName "+templateName+" configsetName "+configsetName);
        SendTemplatedEmailRequest req = new SendTemplatedEmailRequest()
                                    .withConfigurationSetName(configsetName)
                                    .withTemplate(templateName)
                                    .withSource(fromMail)
                                    .withDestination(new Destination()
                                                    .withToAddresses(tomail))
                                    .withTemplateData(templateMetadata);

        
        return req;
    }

    private VerifyEmailIdentityRequest getVarifyEmailReq(String emailAddress){
        VerifyEmailIdentityRequest req = new VerifyEmailIdentityRequest()
                                        .withEmailAddress(emailAddress); 
        return req;
    }

}
