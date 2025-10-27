package org.jobjar.feeder.infrastructure.services;


import org.jobjar.feeder.infrastructure.configuration.HttpClientProperties;

import java.net.URI;
import java.util.Map;

public interface HttpClientPropertiesService {
    // Should be called in constructor
    void buildUri();

    HttpClientProperties getProperties();

    URI getUri();

    Map<String, String> getHeaders();

    Map<String, String> getUrl();

    Map<String, String> getParams();
}
