package com.patrakar.patrakar.service.llm;

import com.patrakar.patrakar.model.repository.Dialouge;
import com.patrakar.patrakar.repository.DialougeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConversationRole;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseResponse;
import software.amazon.awssdk.services.bedrockruntime.model.Message;

@Service
public class LlmService {
    private final BedrockRuntimeClient bedrockClient;
    private final String modelId;

    @Autowired
    DialougeRepository dialougeRepository;

    public LlmService(BedrockRuntimeClient bedrockClient, String modelId) {
        this.bedrockClient= BedrockRuntimeClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create()).build();
        this.modelId="anthropic.claude-3-haiku-20240307-v1:0";
    }

    public String getResponse(String input){
//        // Create the input text and embed it in a message object with the user role.
        Message message = Message.builder()
                .content(ContentBlock.fromText(input))
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
            String output= response.output().message().content().get(0).text();
            dialougeRepository.save(Dialouge
                            .builder()
                            .input(input)
                            .output(output).build()
            );
            return output;
        } catch (SdkClientException e) {
            System.err.printf("ERROR: Can't invoke '%s'. Reason: %s", modelId, e.getMessage());
            throw new RuntimeException(e);
        }


    }

}
