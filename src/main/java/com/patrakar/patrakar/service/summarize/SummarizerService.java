package com.patrakar.patrakar.service.summarize;

import com.patrakar.patrakar.model.repository.Topic;
import com.patrakar.patrakar.service.llm.LlmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummarizerService {

    @Autowired
    LlmService llmService;
    public String summarizeData(String data, Topic topic) {
        String instruction=" <INST> extract relevant infomration related to the "+topic.getText()+" and explain in not more then 100 words . Always start by referencing the site name and explain the information like news anchor. Don't try to generate information by yourself </INST>";
        return llmService.getResponse(data + "  "+instruction);
    }
}
