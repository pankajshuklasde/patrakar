package com.patrakar.patrakar.model.repository.dto;

import com.patrakar.patrakar.model.repository.TopicBrief;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewsItemDto {
    String topic;
    List<String> topicNews;
    String summary;
}
