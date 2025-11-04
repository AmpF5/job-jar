package org.jobjar.feeder.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.messaging")
public record MessagingProperties(
       String exchange,
       String routingKey,
       String queue
){}