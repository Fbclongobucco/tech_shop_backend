package com.buccodev.tech_shop.entities;

public enum Roles {
    BASIC("basic"), ADMIN("admin"), CUSTOMER("customer");

    private final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
