package com.patrakar.patrakar.controller;

import com.patrakar.patrakar.model.repository.Topic;
import com.patrakar.patrakar.service.executors.ExecutorService;
import com.patrakar.patrakar.service.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    @Autowired
    ExecutorService executorService;

    @GetMapping
    public ResponseEntity<String> test() throws InterruptedException {
        executorService.runBriefer();
        return ResponseEntity.ok("good");
    }
    @PostMapping
    public ResponseEntity<Topic> addTopic(@RequestBody Topic topic){
        return ResponseEntity.ok(topicService.createTopic(topic));
    }
    @PostMapping("/add-all")
    public ResponseEntity<List<Topic>> addAllTopcis(@RequestBody List<Topic> topics){
        return ResponseEntity.ok(topicService.addAll(topics));
    }
 }
