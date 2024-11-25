package com.example.ordernotificationmodule.api.model;

import java.util.ArrayList;

public class DeliveryTemplate extends Template{
    private String name;
    private String orderID;

    public DeliveryTemplate(ArrayList<String> placeholders){
        subject = "Dear %s%n";
        content = "Your order #%s has been delivered.%nThank you for using our store.";
        languages.add(Language.ENGLISH);
        type = "Delivery Template";
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
}
