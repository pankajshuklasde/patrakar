package com.patrakar.patrakar.service.topicData;

import com.patrakar.patrakar.model.repository.TopicData;
import com.patrakar.patrakar.repository.TopicDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicDataService {
    @Autowired
    TopicDataRepository topicDataRepository;
    public void save(TopicData topicData) {
        topicDataRepository.save(topicData);
    }
}
