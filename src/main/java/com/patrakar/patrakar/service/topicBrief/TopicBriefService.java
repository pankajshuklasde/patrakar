package com.patrakar.patrakar.service.topicBrief;

import com.patrakar.patrakar.model.repository.Topic;
import com.patrakar.patrakar.model.repository.TopicBrief;
import com.patrakar.patrakar.model.repository.TopicData;
import com.patrakar.patrakar.repository.TopicBriefRepository;
import com.patrakar.patrakar.service.summarize.SummarizerService;
import com.patrakar.patrakar.service.topic.TopicService;
import com.patrakar.patrakar.service.topicData.TopicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicBriefService {

    @Autowired
    TopicService topicService;
    @Autowired
    TopicDataService topicDataService;

    @Autowired
    SummarizerService summarizerService;
    @Autowired
    TopicBriefRepository topicBriefRepository;

    public List<TopicBrief> generateBriefs(List<Topic> topics){
       List<TopicBrief> briefs=new ArrayList<>();
       topics.forEach(topic -> briefs.add(generateBrief(topic)));
       return briefs;
    }

    TopicBrief generateBrief(Topic topic){

        LocalDate to=LocalDate.now();
        LocalDate from=to.minusDays(7);
        // check if breif is present
        TopicBrief topicBrief = findTopicBrief(topic, from, to);
        if (topicBrief != null) return topicBrief;
        // get this week topicDatas
        List<TopicData> topicDatas=topicDataService.findAllTopicDataByTopicId(topic.getId());
        topicDatas=topicDatas.stream().filter(topicData ->
                        topicData.getCreatedAt().isAfter(from.atStartOfDay()) &&
                       ( topicData.getCreatedAt().isBefore(to.atStartOfDay())) ||  topicData.getCreatedAt().isEqual(to.atStartOfDay()))
                .collect(Collectors.toList());
        return topicBriefRepository.save(TopicBrief.builder()
                .topicId(topic.getId())
                .topicDataIds(topicDatas.stream().map(TopicData::getTopicId).collect(Collectors.toList()))
                .brief(summarizerService.generateBriefSummary(topicDatas))
                .from(from)
                .to(to)
                .build());
    }

    private TopicBrief findTopicBrief(Topic topic, LocalDate from, LocalDate to) {
        return topicBriefRepository.findBriefByTopicIdAndDateRange(topic.getId(), from, to);
    }

}
