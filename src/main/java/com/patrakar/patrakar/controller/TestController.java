package com.patrakar.patrakar.controller;

import com.patrakar.patrakar.service.browser.BrowserService;
import com.patrakar.patrakar.service.scraper.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    BrowserService browserService;

    @Autowired
    ScraperService scraperService;

    @GetMapping("/test")
    public ResponseEntity<List<String>> test(@RequestParam("query") String query){
        return ResponseEntity.ok(browserService.extractData(query));
//        List<String> text=browserService.testService();
//        return ResponseEntity.ok(scraperService.testScrape(new ArrayList<>()));
    }
}
