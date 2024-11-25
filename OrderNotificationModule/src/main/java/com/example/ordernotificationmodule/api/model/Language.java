package com.example.ordernotificationmodule.api.model;

public enum Language {
    ARABIC("Arabic"),
    ENGLISH("English");

    private final String description;

    Language(String description){
        this.description = description;
    }

    @Override
    public String toString(){
        return description;
    }
}
