package com.buccodev.tech_shop.exceptions;

public class CredentialInvalidException extends RuntimeException {
    public CredentialInvalidException(String invalidCredentials) {
        super(invalidCredentials);
    }
}
