package org.jobjar.feeder.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
@EnableConfigurationProperties(MessagingProperties.class)
@RequiredArgsConstructor
public class RabbitConnectionConfig {
    private final MessagingProperties messagingProperties;
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
        rt.setExchange(messagingProperties.exchange());
        rt.setRoutingKey(messagingProperties.routingKey());
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
