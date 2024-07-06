package com.patrakar.patrakar.service.scraper;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;
import software.amazon.awssdk.services.bedrock.BedrockClientBuilder;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConversationRole;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseResponse;
import software.amazon.awssdk.services.bedrockruntime.model.Message;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScraperService {
    private final BedrockRuntimeClient bedrockClient;
    private final String modelId;


    ScraperService(){
         this.bedrockClient= BedrockRuntimeClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create()).build();
         this.modelId="anthropic.claude-3-haiku-20240307-v1:0";
    }


    public List<String> testScrape(List<String> rawText,String query){
        List<String> summarized=new ArrayList<>();
        String instruction="extract relevant infomration related to the "+query+" and summarize in not more then 200 words only";
        rawText.forEach(text->{
            summarized.add(getResponse(text,instruction));
        });
        return summarized;
    }
    
    public String getResponse(String prompt , String instruction){
//        // Create the input text and embed it in a message object with the user role.
        Message message = Message.builder()
                .content(ContentBlock.fromText(prompt+" "+instruction))
                .role(ConversationRole.USER)
                .build();
        try {
            // Send the message with a basic inference configuration.
            ConverseResponse response = bedrockClient.converse(request -> request
                    .modelId(modelId)
                    .messages(message)
                    .inferenceConfig(config -> config
                            .maxTokens(512)
                            .temperature(0.5F)
                            .topP(0.9F)));

            // Retrieve the generated text from Bedrock's response object.
            return response.output().message().content().get(0).text();
        } catch (SdkClientException e) {
            System.err.printf("ERROR: Can't invoke '%s'. Reason: %s", modelId, e.getMessage());
            throw new RuntimeException(e);
        }


    }

}
