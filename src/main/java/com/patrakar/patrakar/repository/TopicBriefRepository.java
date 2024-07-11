package com.patrakar.patrakar.repository;

import com.patrakar.patrakar.model.repository.TopicBrief;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicBriefRepository extends MongoRepository<TopicBrief, String> {
}
