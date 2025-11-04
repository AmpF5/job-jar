package org.jobjar.feeder.infrastructure.queues;

import lombok.RequiredArgsConstructor;
import org.jobjar.feeder.infrastructure.configuration.MessagingProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(MessagingProperties.class)
@RequiredArgsConstructor
public class OfferAmqpTopology {
    private final MessagingProperties messagingProperties;

    private final RabbitTemplate rabbitTemplate;

    @Bean
    DirectExchange offersExchange() {
        return new DirectExchange(messagingProperties.exchange(), true, false);
    }

    @Bean
    Queue offersQueue() {
        return new Queue(messagingProperties.queue(), true);
    }

    @Bean
    Binding offersBinding(Queue q, DirectExchange dex) {
        return BindingBuilder.bind(q).to(dex).with(messagingProperties.routingKey());
    }

}
