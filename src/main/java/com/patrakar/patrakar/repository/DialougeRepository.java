package com.patrakar.patrakar.repository;

import com.patrakar.patrakar.model.repository.Dialouge;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DialougeRepository extends MongoRepository<Dialouge, String> {
}
