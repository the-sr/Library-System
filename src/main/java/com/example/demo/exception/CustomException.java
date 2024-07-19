package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorMessage dto;

    public CustomException(String message, HttpStatus status) {
        super(message);
        dto=ErrorMessage.builder().statusCode(status).message(message).build();
    }

}
