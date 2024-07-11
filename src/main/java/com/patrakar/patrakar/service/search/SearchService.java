package com.patrakar.patrakar.service.search;

import com.patrakar.patrakar.model.repository.Topic;
import com.patrakar.patrakar.service.browser.BrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    BrowserService browserService;

    public List<String> searchTopic(Topic topic) {
        return browserService.getLinksFromUrl(getSearchQuery(topic.getText()));
    }

    private String getSearchQuery(String text) {
        return "https://google.com/search?q="+text+"&tbm=nws&num=5&tbs=qdr:d";
    }
}
