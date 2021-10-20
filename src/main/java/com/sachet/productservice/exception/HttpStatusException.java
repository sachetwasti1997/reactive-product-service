package com.sachet.productservice.exception;

import java.time.LocalDateTime;

public class HttpStatusException extends Exception{

    private LocalDateTime localDateTime = LocalDateTime.now();
    private String message;
    private int errorCode;

    public HttpStatusException(String message, int errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "HttpStatusException{" +
                "localDateTime=" + localDateTime +
                ", message='" + message + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
