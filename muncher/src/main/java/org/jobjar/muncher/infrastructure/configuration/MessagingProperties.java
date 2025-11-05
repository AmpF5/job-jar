package org.jobjar.muncher.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.messaging")
public record MessagingProperties(
        String exchange,
        String routingKey,
        String queue
) {
}