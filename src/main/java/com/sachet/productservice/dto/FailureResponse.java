package com.sachet.productservice.dto;

import org.springframework.http.HttpStatus;

public class FailureResponse extends Response{

    public FailureResponse(String message, HttpStatus status) {
        super(message, status);
    }
}
