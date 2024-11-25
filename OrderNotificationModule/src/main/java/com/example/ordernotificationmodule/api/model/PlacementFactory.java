package com.example.ordernotificationmodule.api.model;

import java.util.ArrayList;

public class PlacementFactory implements TemplateFactory{
    @Override
    public Template createTemplate(String type, ArrayList<String> placeholders) {
        if(type.equals("Confirmation")){
            return new ConfirmationTemplate(placeholders);
        } else if (type.equals("Delay")) {
            return new DelayTemplate(placeholders);
        }
        return null;
    }
}
