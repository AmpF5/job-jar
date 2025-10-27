package org.jobjar.feeder.infrastructure.configuration;

import models.enums.HttpClientName;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class HttpClientPropertiesConfig {

    @Bean
    @ConfigurationProperties("http-clients")
    public Map<HttpClientName, HttpClientProperties> httpClients() {
        return new LinkedHashMap<>();
    }
}

