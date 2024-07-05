package com.patrakar.patrakar.service.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BrowserService {

    public List<String> getSiteLinks(String query){
        FirefoxOptions firefoxOptions=new FirefoxOptions();
        firefoxOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG);
        RemoteWebDriver webDriver=new RemoteWebDriver(firefoxOptions);
        webDriver.get("https://google.com/search?q="+query+"&tbm=nws&num=50&tbs=qdr:d");
        List<WebElement> webElements=webDriver.findElements(By.id("search"));
        List<String> siteLinks=webElements.stream()
                .map(webElement -> webElement.findElements(By.tagName("a")))
                .flatMap(Collection::stream)
                .map(webElement -> webElement.getAttribute("href"))
                .toList();
        webDriver.quit();
        return siteLinks;
    }

    public List<String> extractData(String query){
     List<String> siteLinks=getSiteLinks(query);
     List<String> siteData=new ArrayList<>();
     siteLinks.forEach(link->siteData.add(getContext(link)));
     return siteData;
    }

    public String getContext(String siteLink){
        FirefoxOptions firefoxOptions=new FirefoxOptions();
        firefoxOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG);
        RemoteWebDriver webDriver=new RemoteWebDriver(firefoxOptions);
        webDriver.get(siteLink);
        WebElement webElement=webDriver.findElement(By.tagName("body"));
        String data= webElement.getText();
        webDriver.quit();
        return data;
    }

}
