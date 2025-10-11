package org.jobjar.jobjarapi.infrastructure.queues;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OfferAmqpTopology {
    public static final String EXCHANGE = "job-offers";
    public static final String ROUTING_KEY = "collected";
    public static final String QUEUE = "job-offers.collected";

    private final RabbitTemplate rabbitTemplate;

    @Bean
    DirectExchange offersExchange() { return new DirectExchange(EXCHANGE, true, false); }

    @Bean
    Queue offersQueue() { return new Queue(QUEUE, true); }

    @Bean
    Binding offersBinding(Queue q, DirectExchange dex) {
        return BindingBuilder.bind(q).to(dex).with(ROUTING_KEY);
    }

}
