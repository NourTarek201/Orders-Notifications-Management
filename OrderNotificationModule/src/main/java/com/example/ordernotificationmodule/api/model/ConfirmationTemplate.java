package com.example.ordernotificationmodule.api.model;

import java.util.ArrayList;

public class ConfirmationTemplate extends Template{
    private String name;
    private String orderID;

    public ConfirmationTemplate(ArrayList<String> placeholders){
        subject = "Dear %s%n";
        content = "Your order #%s has been been confirmed.%nThank you for using our store.";
        type = "Confirmation Template";
        languages.add(Language.ENGLISH);
        this.placeholders = placeholders;
        this.format();
    }

    @Override
    public void format() {
        name = placeholders.get(0);
        orderID = placeholders.get(1);
        subject = String.format(subject, name);
        content = String.format(content, orderID);
    }

    @Override
    public String getMessage() {
        return subject+content;
    }
}
