package com.example.test.security;

import lombok.Getter;

@Getter
public class CustomUserPrincipal {
    private final Long userId;
    private final String role;

    public CustomUserPrincipal(Long userId, String role) {
        this.userId = userId;
        this.role = role;
    }
}