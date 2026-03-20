package org.training.sequence.generator.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.sequence.generator.model.entity.Sequence;

@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Sequence create() {
        Sequence sequence = new Sequence();
        // Simulate sequence generation
        sequence.setId(1L);
        sequence.setName("TEST_SEQ");
        
        // Send to RabbitMQ
        rabbitTemplate.convertAndSend("sequence.exchange", "sequence.routing.key", sequence);
        
        return sequence;
    }
}