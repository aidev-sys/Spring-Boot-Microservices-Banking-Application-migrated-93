package org.training.fundtransfer.exception;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsufficientBalance {

    public static final String EXCHANGE_NAME = "fund.transfer.exchange";
    public static final String QUEUE_NAME = "fund.transfer.queue";
    public static final String ROUTING_KEY = "fund.transfer.routing.key";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendInsufficientBalanceEvent(InsufficientBalanceEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, event);
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void handleInsufficientBalanceEvent(InsufficientBalanceEvent event) {
        // Handle the insufficient balance event
        System.out.println("Received insufficient balance event: " + event);
    }
}