package org.jobjar.feeder.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jobjar.feeder.infrastructure.queues.OfferAmqpTopology;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class RabbitConnectionConfig {
    @Bean
    ConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {
        var cf = new CachingConnectionFactory();
        cf.setHost(rabbitProperties.getHost());
        cf.setPort(rabbitProperties.getPort());
        cf.setUsername(rabbitProperties.getUsername());
        cf.setPassword(rabbitProperties.getPassword());
        return cf;
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
        var rt = new RabbitTemplate();
        rt.setConnectionFactory(cf);
        rt.setExchange(OfferAmqpTopology.EXCHANGE);
        rt.setRoutingKey(OfferAmqpTopology.ROUTING_KEY);
        rt.setMessageConverter(messageConverter(new ObjectMapper()));
        return rt;
    }

    MessageConverter messageConverter(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
