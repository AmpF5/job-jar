package org.jobjar.feeder.infrastructure.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobjar.feeder.infrastructure.configuration.MessagingProperties;
import org.jobjar.feeder.models.generics.OfferCreateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import utils.TimeConverter;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(MessagingProperties.class)
@Slf4j
public class OfferPublisher {
    private final static int BATCH_SIZE = 200;
    private final RabbitTemplate rabbitTemplate;
    private final MessagingProperties messagingProperties;

    public void publish(OfferCreateDto offer) {
        rabbitTemplate.convertAndSend(messagingProperties.exchange(), messagingProperties.routingKey(), offer);
    }

    public void publishBatch(List<OfferCreateDto> offers) {
        var start = System.nanoTime();

        for (int i = 0; i < offers.size(); i += BATCH_SIZE) {
            var batch = offers.subList(i, Math.min(offers.size(), i + BATCH_SIZE));

            rabbitTemplate.convertAndSend(messagingProperties.exchange(), messagingProperties.routingKey(), batch);
        }

        var end = System.nanoTime();
        log.info("Successfully send {} offers in {} ms", offers.size(), TimeConverter.getElapsedTime(start, end));
    }
}
