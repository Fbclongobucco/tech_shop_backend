package com.buccodev.tech_shop.entities;

public enum OrderStatus {

    CREATED,
    PENDING,
    SHIPPED,
    DELIVERED,
    CANCELED;

    public String getStatus() {
        return this.name();
    }

}
