package org.jobjar.jobjarapi.infrastructure;

import org.jobjar.jobjarapi.domain.configuration.HttpClientName;
import org.jobjar.jobjarapi.domain.configuration.HttpClientProperties;
import org.jobjar.jobjarapi.domain.configuration.HttpClientPropertiesConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
public class HttpClientPropertiesService {
    private final HttpClientPropertiesConfig config;

    public HttpClientPropertiesService(HttpClientPropertiesConfig config) {
        this.config = config;
    }

    public HttpClientProperties getProperties(HttpClientName httpClientName) {
        return config.httpClients().get(httpClientName);
    }

    public URI getUri(HttpClientName httpClientName) {
        var properties = getProperties(httpClientName);

        var baseUrl = String.join("/",getUrl(httpClientName).values());
        var uriComponentBuilder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(baseUrl);

        getParams(httpClientName).forEach(uriComponentBuilder::queryParam);

        return uriComponentBuilder.build().toUri();
    }

    public Map<String, String> getHeaders(HttpClientName httpClientName) {
        return getProperties(httpClientName).headers();
    }

    public Map<String, String> getUrl(HttpClientName httpClientName) {
        return getProperties(httpClientName).url();
    }

    public Map<String, String> getParams(HttpClientName httpClientName) {
        return getProperties(httpClientName).params();
    }
}
