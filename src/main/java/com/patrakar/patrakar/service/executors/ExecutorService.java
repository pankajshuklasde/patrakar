package com.patrakar.patrakar.service.executors;

import com.patrakar.patrakar.model.repository.Topic;
import com.patrakar.patrakar.model.repository.TopicData;
import com.patrakar.patrakar.service.llm.LlmService;
import com.patrakar.patrakar.service.scraper.ScraperService;
import com.patrakar.patrakar.service.search.SearchService;
import com.patrakar.patrakar.service.summarize.SummarizerService;
import com.patrakar.patrakar.service.topic.TopicService;
import com.patrakar.patrakar.service.topicBrief.TopicBriefService;
import com.patrakar.patrakar.service.topicData.TopicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
    TopicDataService topicDataService;

    @Autowired
    TopicBriefService topicBriefService;

    @Autowired
    SummarizerService summarizerService;

    public void runCollector() throws InterruptedException {
        // get all topics
        List<Topic> topics=topicService.getAllTopics();
        for(Topic topic: topics){
            // check past 24 news
            List<String> links=searchService.searchTopic(topic);
            links = links.stream().filter(link->!topic.getVisitedLinks().contains(link)).collect(Collectors.toList());
            for(String link:links){
                // scrape data
                String data=scraperService.scrapeData(link);
                Thread.sleep(Duration.ofSeconds(5));
                // is not relevant data then skip
                if(!isRelevantData(data,topic)) continue;
                // summarize relevant data
                String summarizedData= summarizerService.summarizeData(data,topic);
                // save the data
                topicDataService.save(TopicData.builder()
                        .topicId(topic.getId())
                        .data(summarizedData)
                        .link(link)
                        .build());
                topic.getVisitedLinks().add(link);
                topicService.save(topic);
            }
        }
    }

    private boolean isRelevantData(String data,Topic topic) {
        String filterInstruction="<INST> If the data contains information related to "+topic.getText()+" then just print yes else print no . Always print either yes or no </INST>";
        return llmService.getResponse(data+" "+filterInstruction).toLowerCase().contains("yes");
    }

}
