package com.patrakar.patrakar.repository;

import com.patrakar.patrakar.model.repository.TopicBrief;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;

public interface TopicBriefRepository extends MongoRepository<TopicBrief, String> {
    @Query("{ 'topicId':?0 , 'from':?1,'to':?2 } ")
    TopicBrief findBriefByTopicIdAndDateRange(String id, LocalDate from, LocalDate to);
}
