package org.training.user.service.service;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    private static final String USER_CREATED_EXCHANGE = "user.created.exchange";
    private static final String USER_CREATED_ROUTING_KEY = "user.created.routing.key";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Integer createUser(UserRepresentation userRepresentation) {
        rabbitTemplate.convertAndSend(USER_CREATED_EXCHANGE, USER_CREATED_ROUTING_KEY, userRepresentation);
        return 1;
    }

    @Override
    public List<UserRepresentation> readUserByEmail(String emailId) {
        return null;
    }

    @Override
    public List<UserRepresentation> readUsers(List<String> authIds) {
        return null;
    }

    @Override
    public UserRepresentation readUser(String authId) {
        return null;
    }

    @Override
    public void updateUser(UserRepresentation userRepresentation) {
    }
}