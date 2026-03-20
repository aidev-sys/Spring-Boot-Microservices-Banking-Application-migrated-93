package org.training.account.service.external;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.training.account.service.model.dto.external.UserDto;

@Configuration
public class UserService {

    public static final String USER_QUEUE = "user.queue";

    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE, false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    public ResponseEntity<UserDto> readUserById(Long userId) {
        // Implementation would involve sending a message to RabbitMQ queue
        // and awaiting response via a callback or direct response mechanism
        return null;
    }
}