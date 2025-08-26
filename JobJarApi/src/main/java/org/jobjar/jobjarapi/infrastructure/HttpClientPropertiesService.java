package org.jobjar.jobjarapi.infrastructure;

import org.jobjar.jobjarapi.domain.configuration.HttpClientName;
import org.jobjar.jobjarapi.domain.configuration.HttpClientProperties;
import org.jobjar.jobjarapi.domain.configuration.HttpClientPropertiesConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class HttpClientPropertiesService {
    private final HttpClientPropertiesConfig config;

    public HttpClientPropertiesService(HttpClientPropertiesConfig config) {
        this.config = config;
    }

    public HttpClientProperties get(HttpClientName httpClientName) {
        return config.httpClients().get(httpClientName);
    }

    public String getUri(HttpClientName httpClientName) {
        var properties = get(httpClientName);

        var baseUrl = String.join("/", properties.url().values());
        var uriComponentBuilder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(baseUrl);

        properties.params().forEach(uriComponentBuilder::queryParam);

        return uriComponentBuilder.build().toString();
    }
}
