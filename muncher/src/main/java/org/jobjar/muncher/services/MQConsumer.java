package org.jobjar.muncher.services;

import lombok.RequiredArgsConstructor;
import org.jobjar.muncher.infrastructure.configuration.MessagingProperties;
import org.jobjar.muncher.models.dtos.OfferCreateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(MessagingProperties.class)
public class MQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MQConsumer.class);
    private final OfferService offerService;

    @RabbitListener(queues = "${app.messaging.queue}")
    public void receive(List<OfferCreateDto> offers) {
        logger.info("Received offers {}", offers.size());
        offerService.handleOffers(offers);
    }
}
