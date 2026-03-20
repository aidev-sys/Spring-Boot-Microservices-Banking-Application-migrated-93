package org.training.user.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {

    public ResourceConflictException() {
        super("Resource already present on the server!!!");
    }

    public ResourceConflictException(String message) {
        super(message);
    }
}