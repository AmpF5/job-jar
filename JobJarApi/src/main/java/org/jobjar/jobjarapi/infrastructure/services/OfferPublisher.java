package org.jobjar.jobjarapi.infrastructure.services;

import lombok.RequiredArgsConstructor;
import org.jobjar.jobjarapi.domain.dtos.OfferCreateDto;
import org.jobjar.jobjarapi.infrastructure.queues.OfferAmqpTopology;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publish(OfferCreateDto offer) {
        rabbitTemplate.convertAndSend(OfferAmqpTopology.EXCHANGE, OfferAmqpTopology.ROUTING_KEY, offer);
    }
}
