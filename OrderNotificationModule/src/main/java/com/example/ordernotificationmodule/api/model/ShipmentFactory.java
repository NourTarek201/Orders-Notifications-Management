package com.example.ordernotificationmodule.api.model;

import java.util.ArrayList;

public class ShipmentFactory implements TemplateFactory{
    @Override
    public Template createTemplate(String type, ArrayList<String> placeholders) {
        if(type.equals("Shipment")){
            return new ShipmentTemplate(placeholders);
        } else if (type.equals("Delivery")) {
            return new DeliveryTemplate(placeholders);
        }
        return null;
    }
}
