package com.patrakar.patrakar.service.executors;

import com.patrakar.patrakar.model.repository.Topic;
import com.patrakar.patrakar.model.repository.TopicBrief;
import com.patrakar.patrakar.model.repository.TopicData;
import com.patrakar.patrakar.model.repository.dto.NewsItemDto;
import com.patrakar.patrakar.model.repository.dto.NewsLetterDto;
import com.patrakar.patrakar.service.aws.AwsSesService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
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

    @Autowired
    AwsSesService awsSesService;

    public void runCollector() throws InterruptedException {
        // get all topics
        List<Topic> topics=topicService.getAllTopics();
        for(Topic topic: topics){
            // check past 24 news
            List<String> links=searchService.searchTopic(topic);
            links=removeDuplicates(links);
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

    private List<String> removeDuplicates(List<String> links) {
        if(links.isEmpty()) return new ArrayList<>();
        HashSet<String> temp = new HashSet<>(links);
        return temp.stream().toList();
    }

    public void runBriefer(){
       List<TopicBrief> topicBriefs= topicBriefService.generateBriefs(topicService.getAllTopics());
       NewsLetterDto newsLetter=NewsLetterDto.builder().newsItemDto(
               topicBriefs.stream().map(this::creatNewsItem).collect(Collectors.toList())
       ).build();
       awsSesService.sendEmail(
               "tech@mstack.in",
               "pankaj@mstack.in",
               "Mstack news letter "+LocalDate.now(ZoneId.of("Asia/Kolkata")),
               newsLetter.asHtml(),
               null
       );
    }

    private NewsItemDto creatNewsItem(TopicBrief topicBrief) {
        NewsItemDto itemDto= NewsItemDto.builder()
                .summary(topicBrief.getBrief())
                .topic(topicService.getTopicById(topicBrief.getTopicId()).getMainTopic())
                .topicNews(topicDataService.findAllByIds(topicBrief.getTopicDataIds()).stream().map(TopicData::getData)
                        .collect(Collectors.toList()))
                .build();
        return itemDto;
    }


    private boolean isRelevantData(String data,Topic topic) {
        String filterInstruction="<INST> If the data contains information related to "+topic.getMainTopic()+" then just print yes else print no . Always print either yes or no </INST>";
        return llmService.getResponse(data+" "+filterInstruction).equalsIgnoreCase("yes");
    }

}
