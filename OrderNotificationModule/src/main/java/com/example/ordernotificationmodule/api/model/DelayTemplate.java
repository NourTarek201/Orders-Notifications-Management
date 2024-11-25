package com.example.ordernotificationmodule.api.model;

import java.util.ArrayList;

public class DelayTemplate extends Template{
    private String name;
    private String orderID;
    private String contact;

    public DelayTemplate(ArrayList<String> placeholders){
        subject = "Dear %s%n";
        content = "There might be a slight delay in processing your order #%s. Contact us at %s for further assistance.";
        type = "Delay Template";
        languages.add(Language.ENGLISH);
        this.placeholders = placeholders;
        this.format();
    }

    @Override
    public void format() {
        name = placeholders.get(0);
        orderID = placeholders.get(1);
        contact = "store@email.com";
        subject = String.format(subject, name);
        contact = String.format(content, orderID, contact);
    }
}
