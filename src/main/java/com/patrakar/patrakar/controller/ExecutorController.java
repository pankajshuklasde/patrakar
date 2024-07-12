package com.patrakar.patrakar.controller;

import com.patrakar.patrakar.service.executors.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/executor")
public class ExecutorController {
    @Autowired
    ExecutorService executorService;

    @GetMapping("/daily-brief")
    public ResponseEntity<String> runBrief() throws InterruptedException {
        executorService.runBriefer();
        return ResponseEntity.ok("You will be recieving shortly on mail");
    }

    @GetMapping("/collector")
    public ResponseEntity<String> runCollector() throws InterruptedException {
        executorService.runCollector();
        return ResponseEntity.ok("ran collectior");
    }
}
