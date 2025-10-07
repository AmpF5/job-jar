package org.jobjar.jobjarapi.infrastructure.services;

import org.jobjar.jobjarapi.domain.enums.HttpClientName;
import org.jobjar.jobjarapi.infrastructure.configuration.HttpClientPropertiesConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientPropertiesServiceConfig {
    private final HttpClientPropertiesConfig config;

    public HttpClientPropertiesServiceConfig(HttpClientPropertiesConfig config) {
        this.config = config;
    }

    @Bean
    @Qualifier("justjoinit")
    public HttpClientPropertiesService justJoinItHttpClientPropertiesService() {
        return new HttpClientPropertiesServiceImpl(HttpClientName.JUST_JOIN_IT, config);
    }

    @Bean
    @Qualifier("theprotocol")
    public HttpClientPropertiesService theProtocolHttpClientPropertiesService() {
        return new HttpClientPropertiesServiceImpl(HttpClientName.THE_PROTOCOL, config);
    }
}
