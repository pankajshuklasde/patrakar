package com.patrakar.patrakar.model.repository;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TopicBrief {
    @Id
    private String id;
    private String topicId;
    private List<String> topicDataIds;
    private String brief;
    private LocalDate from;
    private LocalDate to;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
