package com.patrakar.patrakar.repository;

import com.patrakar.patrakar.model.repository.TopicData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TopicDataRepository extends MongoRepository<TopicData, String> {
    @Query("{ 'topicId': ?0 } ")
    List<TopicData> findAllTopicDataByTopicId(String id);
}
