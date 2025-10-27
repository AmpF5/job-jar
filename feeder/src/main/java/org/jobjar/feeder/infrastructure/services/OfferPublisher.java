package org.jobjar.feeder.infrastructure.services;

import lombok.RequiredArgsConstructor;
import models.dtos.OfferCreateDto;
import org.jobjar.feeder.infrastructure.queues.OfferAmqpTopology;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferPublisher {
    private final static int BATCH_SIZE = 200;
    private final RabbitTemplate rabbitTemplate;

    public void publish(OfferCreateDto offer) {
        rabbitTemplate.convertAndSend(OfferAmqpTopology.EXCHANGE, OfferAmqpTopology.ROUTING_KEY, offer);
    }

    public void publishBatch(List<OfferCreateDto> offers) {
        var batches = new ArrayList<List<OfferCreateDto>>();
        for (int i = 0; i < offers.size(); i += BATCH_SIZE) {
            var batch = offers.subList(i, Math.min(offers.size(), i + BATCH_SIZE));

            rabbitTemplate.convertAndSend(OfferAmqpTopology.EXCHANGE, OfferAmqpTopology.ROUTING_KEY, batch);
        }
    }
}
