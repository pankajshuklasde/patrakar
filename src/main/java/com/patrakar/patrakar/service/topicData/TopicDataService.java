package com.patrakar.patrakar.service.topicData;

import com.patrakar.patrakar.model.repository.TopicData;
import com.patrakar.patrakar.repository.TopicDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicDataService {
    @Autowired
    TopicDataRepository topicDataRepository;
    public void save(TopicData topicData) {
        topicDataRepository.save(topicData);
    }

    public List<TopicData> findAllTopicDataByTopicId(String id) {
        return topicDataRepository.findAllTopicDataByTopicId(id);
    }

    public List<TopicData> findAllByIds(List<String> topicDataIds) {
        return topicDataRepository.findAllById(topicDataIds);
    }
}
