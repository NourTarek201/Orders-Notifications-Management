package com.example.ordernotificationmodule.api.model;

import java.util.ArrayList;

public abstract class Template {
    protected String subject;
    protected String content;
    protected String type;
    protected ArrayList<Language> languages = new ArrayList<>();
    protected ArrayList<String> placeholders = new ArrayList<>();

    public abstract void format();

    public String getMessage(){
        return subject+content;
    }
    public String getType(){
        return type;
    }
}
