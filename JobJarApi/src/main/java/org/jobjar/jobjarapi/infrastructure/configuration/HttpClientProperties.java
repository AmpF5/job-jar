package org.jobjar.jobjarapi.infrastructure.configuration;

import java.util.Map;

public record HttpClientProperties(
        Map<String, String> headers,
        Map<String, String> url,
        Map<String, String> params
) {
}
