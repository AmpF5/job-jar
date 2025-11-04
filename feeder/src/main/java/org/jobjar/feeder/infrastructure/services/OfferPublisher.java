package org.jobjar.feeder.infrastructure.services;

import lombok.RequiredArgsConstructor;
import org.jobjar.feeder.infrastructure.configuration.MessagingProperties;
import org.jobjar.feeder.models.generics.OfferCreateDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(MessagingProperties.class)
public class OfferPublisher {
    private final static int BATCH_SIZE = 200;
    private final RabbitTemplate rabbitTemplate;
    private final MessagingProperties messagingProperties;

    public void publish(OfferCreateDto offer) {
        rabbitTemplate.convertAndSend(messagingProperties.exchange(), messagingProperties.routingKey(), offer);
    }

    public void publishBatch(List<OfferCreateDto> offers) {
        var batches = new ArrayList<List<OfferCreateDto>>();
        for (int i = 0; i < offers.size(); i += BATCH_SIZE) {
            var batch = offers.subList(i, Math.min(offers.size(), i + BATCH_SIZE));

            rabbitTemplate.convertAndSend(messagingProperties.exchange(), messagingProperties.routingKey(), batch);
        }
    }
}
