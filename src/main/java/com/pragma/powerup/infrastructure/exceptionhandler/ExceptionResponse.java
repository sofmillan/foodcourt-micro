package com.pragma.powerup.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    DATA_NOT_FOUND("No data found for the requested petition"),
    DATA_ALREADY_EXISTS("Some data already exists"),
    NOT_MODIFIABLE("You cannot update some of these fields"),
    FORBIDDEN("You do not have access to this request"),
    DATA_NOT_VALID("Some data is not valid, check it"),
    NOT_REGISTERED("You are not registered");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}