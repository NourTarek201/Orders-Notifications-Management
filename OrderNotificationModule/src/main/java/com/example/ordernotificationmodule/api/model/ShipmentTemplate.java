package com.example.ordernotificationmodule.api.model;

import java.util.ArrayList;

public class ShipmentTemplate extends Template{
    private String name;
    private String orderID;
    private String carrier;

    public ShipmentTemplate(ArrayList<String> placeholders){
        subject = "Dear %s%n";
        content = "Your order #%s has been shipped. Your carrier is %s.%nThank you for using our store.";
        type = "Shipment Template";
        languages.add(Language.ENGLISH);
        this.placeholders = placeholders;
        this.format();
    }

    @Override
    public void format() {
        name = placeholders.get(0);
        orderID = placeholders.get(1);
        carrier = "FedEx";
        subject = String.format(subject, name);
        content = String.format(content, orderID, carrier);
    }

}
