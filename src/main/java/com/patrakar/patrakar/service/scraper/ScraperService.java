package com.patrakar.patrakar.service.scraper;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.patrakar.patrakar.service.browser.BrowserService;
import com.patrakar.patrakar.service.llm.LlmService;
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
import java.util.stream.Collectors;

@Service
public class ScraperService {
    @Autowired
    BrowserService browserService;

    public String scrapeData(String link) {
        return browserService.getContext(link) ;
    }
}
