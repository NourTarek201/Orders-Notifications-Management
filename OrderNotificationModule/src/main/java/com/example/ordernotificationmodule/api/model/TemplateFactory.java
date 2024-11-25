package com.example.ordernotificationmodule.api.model;

import java.util.ArrayList;

public interface TemplateFactory {
    public Template createTemplate(String type, ArrayList<String> placeholders);
}
