package com.patrakar.patrakar.repository;

import com.patrakar.patrakar.model.repository.TopicData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicDataRepository extends MongoRepository<TopicData, String> {
}
