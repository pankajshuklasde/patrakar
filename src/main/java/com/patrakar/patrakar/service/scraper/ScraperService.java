package com.patrakar.patrakar.service.scraper;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.ai.autoconfigure.ollama.OllamaChatProperties;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScraperService {
    @Autowired
    OllamaChatModel ollamaChatModel;

    String instruction="extract relevant data in below format : \n" +
            "[\n" +
            "\t{\n" +
            "\t\tsource : ## source of news,\n" +
            "\t\tcontent : ##content of news,\n" +
            "\t\ttime : ##time of news,\n" +
            "\t}\n" +
            "]";

    String sampleString="";


    public List<Map<String, String>> testScrape(List<String> rawText){
//        List<Map<String, String>> response=new ArrayList<>();
 //        String testString=rawText.getFirst();
        String testString=sampleString;
        String jsonResponse =ollamaChatModel.call(testString);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Map<String, String>>>(){}.getType();
        return gson.fromJson(jsonResponse, listType);
    }

}
