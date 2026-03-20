package org.training.user.service.external;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.training.user.service.model.external.Account;

@Service
public class AccountService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${account.queue.name}")
    private String accountQueueName;

    public ResponseEntity<Account> readByAccountNumber(String accountNumber) {
        // Send message to RabbitMQ queue
        rabbitTemplate.convertAndSend(accountQueueName, accountNumber);

        // Simulate receiving response (in real implementation, this would be async)
        // For now, we'll just return a mock response
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(1000.0);
        return ResponseEntity.ok(account);
    }

    @RabbitListener(queues = "${account.queue.name}")
    public void handleAccountRequest(String accountNumber) {
        // Process the account request
        System.out.println("Received account request for account number: " + accountNumber);
    }

    @Bean
    public Queue accountQueue() {
        return new Queue(accountQueueName, false);
    }
}

@Configuration
class RabbitConfig {

    @Value("${account.queue.name}")
    private String accountQueueName;

    @Bean
    public Queue accountQueue() {
        return new Queue(accountQueueName, false);
    }
}