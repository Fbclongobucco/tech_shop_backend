package com.buccodev.tech_shop.exceptions;

public class JWTException extends RuntimeException {
    public JWTException(String message) {
        super(message);
    }
}
