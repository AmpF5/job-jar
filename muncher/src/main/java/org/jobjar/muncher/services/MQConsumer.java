package org.jobjar.muncher.services;

import lombok.RequiredArgsConstructor;
import org.jobjar.muncher.infrastructure.configuration.MessagingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(MessagingProperties.class)
public class MQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MQConsumer.class);

    @RabbitListener(queues = "${app.messaging.queue}")
    public void receive(String message) {
        System.out.println("Received '" + message + "'");
    }
}
