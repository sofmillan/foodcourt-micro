package com.pragma.powerup.infrastructure.constants;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    CLIENT("ROLE_CLIENT"),
    EMPLOYEE("ROLE_EMPLOYEE"),
    OWNER("ROLE_OWNER");

    private final String message;

    Role(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
