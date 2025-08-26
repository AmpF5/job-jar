package org.jobjar.jobjarapi.infrastructure.clients;

import org.jobjar.jobjarapi.infrastructure.HttpClientPropertiesService;
import org.springframework.stereotype.Service;

@Service
public class JustJoinItHttpClient implements BaseClient {
    private final HttpClientPropertiesService httpClientPropertiesService;

    public JustJoinItHttpClient(HttpClientPropertiesService httpClientPropertiesService) {
        this.httpClientPropertiesService = httpClientPropertiesService;
    }

    @Override
    public void GetRequest() {
//        httpClientProperties.justJointItHttpClientProperties().url()

    }
}
