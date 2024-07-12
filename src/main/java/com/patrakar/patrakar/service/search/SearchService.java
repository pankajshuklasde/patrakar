package com.patrakar.patrakar.service.search;

import com.patrakar.patrakar.model.repository.Topic;
import com.patrakar.patrakar.service.browser.BrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    BrowserService browserService;

    public List<String> searchTopic(Topic topic) {
        List<String> urls= browserService.getLinksFromUrl(getSearchQuery(topic.getText()));
        topic.getSubTopics().forEach(s -> urls.addAll(browserService.getLinksFromUrl(getSearchQuery(s))));
        return urls;
    }

    private String getSearchQuery(String searchText) {
        try {
            String encodedText = URLEncoder.encode(searchText, StandardCharsets.UTF_8);
            String baseUrl = "https://google.com/search";
            String query = String.format("q=%s&tbm=nws&num=5&tbs=qdr:d", encodedText);
            URI uri = new URI(baseUrl + "?" + query);
            return uri.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
