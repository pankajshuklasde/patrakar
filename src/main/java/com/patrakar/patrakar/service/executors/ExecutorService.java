package com.patrakar.patrakar.service.executors;

import com.patrakar.patrakar.model.repository.Topic;
import com.patrakar.patrakar.service.llm.LlmService;
import com.patrakar.patrakar.service.scraper.ScraperService;
import com.patrakar.patrakar.service.search.SearchService;
import com.patrakar.patrakar.service.summarize.SummarizerService;
import com.patrakar.patrakar.service.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExecutorService {
    @Autowired
    TopicService topicService;
    @Autowired
    SearchService searchService;

    @Autowired
    ScraperService scraperService;

    @Autowired
    LlmService llmService;

    @Autowired
    SummarizerService summarizerService;

    private void runCollector(){
        // get all topics
        List<Topic> topics=topicService.getAllTopics();
        for(Topic topic: topics){
            // check past 24 news
            List<String> links=searchService.searchTopic(topic);
            links = links.stream().filter(link->!topic.getVisitedLinks().contains(link)).collect(Collectors.toList());
            for(String link:links){
                // scrape data
                String data=scraperService.scrapeData(link);
                // is not relevant data then skip
                if(!isRelevantData(data)) continue;
                // summarize relevant data
                String summarizedData= summarizerService.summarizeData(data);
                // save the data
                // check next link .
            }
        }
    }

}
