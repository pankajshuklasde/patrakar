package com.patrakar.patrakar.model.repository;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Unwrapped;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Topic {
    @Id
    private String id;
    @NonNull
    private String mainTopic;
    @NonNull
    private List<String> subTopics;
    @NonNull
    private List<String> visitedLinks;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
