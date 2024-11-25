package com.example.ordernotificationmodule.api.model;

import java.util.ArrayList;

public class Notification {
    private Template template;
    private Channel channel;

    public Notification(Template template, Channel channel){
        this.template = template;
        this.channel = channel;
    }

    public String getTemplateType(){
        return template.getType();
    }

    public void sendNotification(){
        channel.send(this);
    }

    public String getMessage(){
        return template.getMessage();
    }

    public ArrayList<String> getRecepients(){
        ArrayList<String> recepients = new ArrayList<>();
        channel.getRecepients(recepients);
        return recepients;
    }
}
