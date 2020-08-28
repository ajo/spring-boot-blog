package com.reljicd.config;

public enum TestCredentials {

    ADMIN("admin", "admin"),

    // username:password are default for @
    USER("username", "password"),
    INVALID("bad","bad");

    private final String username;
    private final String password;

    TestCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
