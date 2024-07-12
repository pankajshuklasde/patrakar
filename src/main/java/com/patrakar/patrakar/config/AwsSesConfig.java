package com.patrakar.patrakar.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@Data
public class AwsSesConfig {

    String region;
    String accessKey;
    String secretKey;
}
