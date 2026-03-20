package org.training.transactions.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndRequeueException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> handleGlobalException(GlobalException globalException) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .errorCode(globalException.getErrorCode())
                        .message(globalException.getMessage())
                        .build());
    }

    @ExceptionHandler(AmqpRejectAndRequeueException.class)
    public ResponseEntity<Object> handleAmqpRejectAndRequeueException(AmqpRejectAndRequeueException exception) {
        log.error("AMQP reject and requeue exception occurred", exception);
        return ResponseEntity
                .status(500)
                .body(ErrorResponse.builder()
                        .errorCode("INTERNAL_ERROR")
                        .message("Message processing failed, retrying...")
                        .build());
    }
}