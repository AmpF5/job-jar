package org.jobjar.jobjarapi.domain.configuration;

public record JustJointItHttpClientProperties (
        Headers headers,
        Params params,
        Url url
) {
    public record Headers(
            String version
    ) {
    }

    public record Params(
            String sortBy,
            int perPage
    ) {
    }

    public record Url (
            String base,
            String version,
            String path
    ) {
    }
}
