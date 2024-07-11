package com.patrakar.patrakar.model.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TopicData {
    @Id
    private String id;
    private String topicId;
    private String link;
    private String data;
    private List<String> chatIds;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
