package com.example.ordernotificationmodule.api.model;

public enum OrderStatus {
    PENDING("Pending"),
    SHIPPED("Shipped"),
    DELIVERED("Shipped"),
    CANCELED("Canceled");

    private final String description;

    OrderStatus(String description){
        this.description = description;
    }

    @Override
    public String toString(){
        return description;
    }
}
