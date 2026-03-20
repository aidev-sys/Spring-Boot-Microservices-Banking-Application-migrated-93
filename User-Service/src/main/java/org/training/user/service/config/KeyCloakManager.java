package org.training.user.service.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeyCloakManager {

    private final KeyCloakProperties keyCloakProperties;
    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;

    /**
     * Returns the KeyCloak instance for the specified realm.
     *
     * @return  the KeyCloak instance for the specified realm
     */
    public RealmResource getKeyCloakInstanceWithRealm() {
        return keyCloakProperties.getKeycloakInstance().realm(keyCloakProperties.getRealm());
    }

    @Bean
    public Queue userQueue() {
        return new Queue("user.queue", false);
    }

    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange("user.exchange");
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder.bind(userQueue()).to(userExchange()).with("user.routing.key");
    }

    @RabbitListener(queues = "user.queue")
    public void handleUserMessage(String message) {
        // Process user message
    }
}