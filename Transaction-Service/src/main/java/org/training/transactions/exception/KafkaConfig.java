package org.training.transactions.exception;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class KafkaConfig {

    @Value("${app.kafka.account-status-topic}")
    private String accountStatusTopic;

    @Bean
    public Queue accountStatusQueue() {
        return new Queue(accountStatusTopic, true);
    }

    @Bean
    public DirectExchange accountStatusExchange() {
        return new DirectExchange(accountStatusTopic);
    }

    @Bean
    public Binding accountStatusBinding(Queue accountStatusQueue, DirectExchange accountStatusExchange) {
        return BindingBuilder.bind(accountStatusQueue).to(accountStatusExchange).with(accountStatusTopic);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
}