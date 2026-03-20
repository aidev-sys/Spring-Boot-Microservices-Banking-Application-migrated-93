package org.training.account.service.external;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.account.service.configuration.RabbitMQConfig;
import org.training.account.service.model.dto.external.TransactionResponse;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<TransactionResponse> getTransactionsFromAccountId(String accountId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TRANSACTION_EXCHANGE, RabbitMQConfig.TRANSACTION_ROUTING_KEY, accountId);
        return null;
    }
}