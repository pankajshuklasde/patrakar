package com.patrakar.patrakar.controller;

import com.patrakar.patrakar.model.repository.Topic;
import com.patrakar.patrakar.service.executors.ExecutorService;
import com.patrakar.patrakar.service.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController("/topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    @Autowired
    ExecutorService executorService;

    @GetMapping
    public ResponseEntity<String> test(){
        executorService.runCollector();
        return ResponseEntity.ok("good");
    }
    @PostMapping
    public ResponseEntity<Topic> addTopic(@RequestBody HashMap<String, Object> body){
        return ResponseEntity.ok(topicService.createTopic((String) body.get("topic")));
    }
}
