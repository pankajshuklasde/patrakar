package com.patrakar.patrakar.controller;

import com.patrakar.patrakar.service.browser.BrowserService;
import com.patrakar.patrakar.service.scraper.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/test")
public class TestController {
    @Autowired
    BrowserService browserService;

    @Autowired
    ScraperService scraperService;

    @GetMapping("/test-bedrock")
    public ResponseEntity<List<String>> test(@RequestParam("query") String query){
        return ResponseEntity.ok(scraperService.testScrape(browserService.extractData(query),query));
    }
}
