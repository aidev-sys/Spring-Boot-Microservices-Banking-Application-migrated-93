package org.training.transactions.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.training.transactions.model.dto.TransactionDto;
import org.training.transactions.model.response.Response;
import org.training.transactions.model.response.TransactionRequest;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${transaction.exchange.name}")
    private String exchangeName;

    @Value("${transaction.routing.key}")
    private String routingKey;

    public Response addTransaction(TransactionDto transactionDto) {
        amqpTemplate.convertAndSend(exchangeName, routingKey, transactionDto);
        return new Response("Transaction submitted successfully");
    }

    public Response internalTransaction(List<TransactionDto> transactionDtos, String transactionReference) {
        // Implementation for internal transaction processing
        return new Response("Internal transaction processed");
    }

    public List<TransactionRequest> getTransaction(String accountId) {
        // Implementation for retrieving transactions by account ID
        return List.of();
    }

    public List<TransactionRequest> getTransactionByTransactionReference(String transactionReference) {
        // Implementation for retrieving transactions by transaction reference
        return List.of();
    }
}