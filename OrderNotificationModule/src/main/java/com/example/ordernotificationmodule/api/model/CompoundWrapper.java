package com.example.ordernotificationmodule.api.model;

public class CompoundWrapper {
    private int id;
    private String password;
    private String type;
    private Compound order;

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public Compound getOrder() {
        return order;
    }
}
