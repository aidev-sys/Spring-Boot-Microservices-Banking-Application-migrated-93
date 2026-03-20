package org.training.transactions.model.mapper;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import org.training.transactions.model.dto.TransactionDto;
import org.training.transactions.model.entity.Transaction;

import java.util.Objects;

@Component
public class TransactionMapper {

    private final RabbitTemplate rabbitTemplate;
    private final Queue transactionQueue;

    public TransactionMapper(RabbitTemplate rabbitTemplate, Queue transactionQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.transactionQueue = transactionQueue;
    }

    public Transaction convertToEntity(TransactionDto dto, Object... args) {
        Transaction transaction = new Transaction();
        if (!Objects.isNull(dto)) {
            BeanUtils.copyProperties(dto, transaction);
        }
        return transaction;
    }

    public TransactionDto convertToDto(Transaction entity, Object... args) {
        TransactionDto transactionDto = new TransactionDto();
        if (!Objects.isNull(entity)) {
            BeanUtils.copyProperties(entity, transactionDto);
        }
        return transactionDto;
    }

    @RabbitListener(queues = "#{transactionQueue.name}")
    public void receiveTransaction(TransactionDto transactionDto) {
        // Handle incoming transaction message
    }

    public void sendTransaction(TransactionDto transactionDto) {
        rabbitTemplate.convertAndSend(transactionQueue.getName(), transactionDto);
    }
}