package org.training.account.service.external;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.account.service.model.dto.external.SequenceDto;

@Service
public class SequenceService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public SequenceService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public SequenceDto generateAccountNumber() {
        rabbitTemplate.convertAndSend("sequence.exchange", "sequence.routing.key", "generate");
        // In a real implementation, you would await the response from the sequence generator
        // This is a simplified placeholder
        return new SequenceDto();
    }
}