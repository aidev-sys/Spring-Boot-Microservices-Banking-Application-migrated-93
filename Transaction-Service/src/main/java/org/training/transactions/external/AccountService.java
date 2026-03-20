package org.training.transactions.external;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.training.transactions.model.external.Account;
import org.training.transactions.model.response.Response;

@Service
public class AccountService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${account.service.queue.name}")
    private String accountQueueName;

    public ResponseEntity<Account> readByAccountNumber(String accountNumber) {
        // Send message to RabbitMQ
        rabbitTemplate.convertAndSend(accountQueueName, "readByAccountNumber:" + accountNumber);

        // Simulate response - in real implementation you'd await response from another service
        return ResponseEntity.ok(new Account());
    }

    public ResponseEntity<Response> updateAccount(String accountNumber, Account account) {
        // Send message to RabbitMQ
        rabbitTemplate.convertAndSend(accountQueueName, "updateAccount:" + accountNumber + ":" + account.toString());

        // Simulate response
        return ResponseEntity.ok(new Response());
    }
}