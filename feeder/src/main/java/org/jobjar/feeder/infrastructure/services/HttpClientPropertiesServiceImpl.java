package org.jobjar.feeder.infrastructure.services;


import models.enums.HttpClientName;
import org.jobjar.feeder.infrastructure.configuration.HttpClientProperties;
import org.jobjar.feeder.infrastructure.configuration.HttpClientPropertiesConfig;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@SuppressWarnings("LombokGetterMayBeUsed")
public class HttpClientPropertiesServiceImpl implements HttpClientPropertiesService {
    private final HttpClientName httpClientName;
    private final HttpClientPropertiesConfig config;
    private URI uri;

    public HttpClientPropertiesServiceImpl(HttpClientName httpClientName, HttpClientPropertiesConfig config) {
        this.httpClientName = httpClientName;
        this.config = config;
        buildUri();
    }

    public void buildUri() {
        var baseUrl = String.join("/", getUrl().values());
        var uriComponentBuilder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(baseUrl);

        getParams().forEach(uriComponentBuilder::queryParam);

        uri = uriComponentBuilder.build().toUri();
    }

    public HttpClientProperties getProperties() {
        return config.httpClients().get(httpClientName);
    }

    public URI getUri() {
        return uri;
    }

    public Map<String, String> getHeaders() {
        return getProperties().headers();
    }

    public Map<String, String> getUrl() {
        return getProperties().url();
    }

    public Map<String, String> getParams() {
        return getProperties().params();
    }
}
