package com.patrakar.patrakar.service.topic;

import com.patrakar.patrakar.model.repository.Topic;
import com.patrakar.patrakar.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {
    @Autowired
    TopicRepository topicRepository;
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic createTopic(Topic topic){
        topic.setVisitedLinks(new ArrayList<>());
        return topicRepository.save(
                topic
        );
    }

    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    public Topic getTopicById(String topicId) {
        return topicRepository.findById(topicId).orElse(null);
    }

    public List<Topic> addAll(List<Topic> topics) {
        return topics.stream().map(this::createTopic).collect(Collectors.toList());
    }
}
