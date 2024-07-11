package com.patrakar.patrakar.repository;

import com.patrakar.patrakar.model.repository.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicRepository extends MongoRepository<Topic,String> {
}

