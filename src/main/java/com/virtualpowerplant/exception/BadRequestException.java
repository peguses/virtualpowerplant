package com.virtualpowerplant.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super();
    }

    public BadRequestException(final String message) {
        super(message);
    }
}
