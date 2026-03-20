package org.training.account.service.model.mapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.training.account.service.model.dto.AccountDto;
import org.training.account.service.model.entity.Account;

import java.util.Objects;

public class AccountMapper extends BaseMapper<Account, AccountDto> {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${account.queue.name}")
    private String queueName;

    @Override
    public Account convertToEntity(AccountDto dto, Object... args) {
        Account account = new Account();
        if (!Objects.isNull(dto)) {
            BeanUtils.copyProperties(dto, account);
        }
        return account;
    }

    @Override
    public AccountDto convertToDto(Account entity, Object... args) {
        AccountDto accountDto = new AccountDto();
        if (!Objects.isNull(entity)) {
            BeanUtils.copyProperties(entity, accountDto);
        }
        return accountDto;
    }

    public void sendAccountMessage(AccountDto accountDto) {
        rabbitTemplate.convertAndSend(queueName, accountDto);
    }

    @RabbitListener(queues = "${account.queue.name}")
    public void receiveAccountMessage(AccountDto accountDto) {
        // Handle received message
    }

    @Configuration
    public static class RabbitConfig {

        @Value("${account.queue.name}")
        private String queueName;

        @Bean
        public Queue accountQueue() {
            return new Queue(queueName, false);
        }
    }
}