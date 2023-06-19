package com.pragma.powerup.infrastructure.constants;

public enum Status {

    PENDING("PENDING"),
    IN_PREPARATION("IN_PREPARATION"),
    READY("READY"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
