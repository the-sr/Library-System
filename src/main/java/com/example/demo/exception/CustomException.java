package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorMessage dto;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
        dto=ErrorMessage.builder().message(message).statusCode(httpStatus.value()).build();
    }
}
