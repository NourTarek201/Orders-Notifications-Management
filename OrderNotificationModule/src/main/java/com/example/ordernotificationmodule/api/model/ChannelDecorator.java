package com.example.ordernotificationmodule.api.model;

import java.util.ArrayList;

public abstract class ChannelDecorator extends Channel{
    protected Channel channel;
    protected String recepient;
    public ChannelDecorator(Channel channel, String recepient){
        this.channel = channel;
        this.recepient = recepient;
    }

    @Override
    public void send(Notification notification){
        channel.send(notification);
    }
    @Override
    public void getRecepients(ArrayList<String> recepients) {
        channel.getRecepients(recepients);
        recepients.add(recepient);
    }
}
