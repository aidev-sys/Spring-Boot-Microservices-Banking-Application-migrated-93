package org.training.account.service.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.training.account.service.exception.GlobalException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class FeignClientErrorDecoder {

    /**
     * Handle the message and return an Exception object.
     *
     * @param message  the message string
     * @param errorCode the error code from the message headers
     * @return an Exception object representing the decoded message
     */
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public Exception decode(@Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey,
                            @Header(AmqpHeaders.RECEIVED_DELIVERY_MODE) Integer deliveryMode,
                            Message message) {

        String jsonString = new String(message.getBody(), StandardCharsets.UTF_8);
        GlobalException globalException = extractGlobalException(jsonString);

        log.info("Received message with routing key: {} and delivery mode: {}", routingKey, deliveryMode);
        if (deliveryMode != null && deliveryMode == 2) { // Assuming 2 means persistent
            log.error("Error in request went through RabbitMQ: {}", globalException.getErrorMessage() + " - " + globalException.getErrorCode());
            return globalException;
        }
        log.error("General exception went through RabbitMQ");
        return new Exception();
    }

    /**
     * Extracts a GlobalException object from the message.
     *
     * @param message The message string containing the exception information
     * @return The GlobalException object extracted from the message, or null if extraction fails
     */
    private GlobalException extractGlobalException(String message) {

        GlobalException globalException = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            globalException = mapper.readValue(message, GlobalException.class);
            log.error(globalException.toString());
        } catch (IOException e) {
            log.error("IO Exception while reading exception message", e);
        }
        return globalException;
    }
}