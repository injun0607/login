package com.alham.login.constant;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String key;

    Role(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
