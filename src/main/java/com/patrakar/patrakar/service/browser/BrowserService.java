package com.patrakar.patrakar.service.browser;

import jakarta.annotation.PreDestroy;
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
    private final RemoteWebDriver webDriver;

    public BrowserService() {
        FirefoxOptions firefoxOptions=new FirefoxOptions();
        firefoxOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG);
        this.webDriver = new RemoteWebDriver(firefoxOptions);
    }

    @PreDestroy
    private void disconnectDrive(){
        System.out.println("destroying connection");
        webDriver.quit();
    }


    public List<String> getSiteLinks(String query){
        webDriver.get("https://google.com/search?q="+query+"&tbm=nws&num=5&tbs=qdr:d");
        List<WebElement> webElements=webDriver.findElements(By.id("search"));
        List<String> siteLinks=webElements.stream()
                .map(webElement -> webElement.findElements(By.tagName("a")))
                .flatMap(Collection::stream)
                .map(webElement -> webElement.getAttribute("href"))
                .toList();
        return siteLinks;
    }

    public List<String>getLinksFromUrl(String url){
        webDriver.get(url);
        List<WebElement> webElements=webDriver.findElements(By.id("search"));
        List<String> siteLinks=webElements.stream()
                .map(webElement -> webElement.findElements(By.tagName("a")))
                .flatMap(Collection::stream)
                .map(webElement -> webElement.getAttribute("href"))
                .toList();
        return siteLinks;
    }

    public List<String> extractData(String query){
     List<String> siteLinks=getSiteLinks(query);
     List<String> siteData=new ArrayList<>();
        for (String link : siteLinks) {
            try{
                String context=getContext(link);
                if(context.isEmpty()|| context.isBlank()) continue;
                siteData.add(context);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("returning site data ");
     return siteData;
    }

    public String getContext(String siteLink){
        try{
            System.out.println("getting context for "+siteLink);
            webDriver.get(siteLink);
            WebElement webElement=webDriver.findElement(By.tagName("body"));
            System.out.println("returing context ");
            return webElement.getText();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return "";
        }

    }

}
