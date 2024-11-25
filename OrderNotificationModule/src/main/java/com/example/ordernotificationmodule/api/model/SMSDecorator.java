package com.example.ordernotificationmodule.api.model;

public class SMSDecorator extends ChannelDecorator{
    public SMSDecorator(Channel channel, String recepient) {
        super(channel, recepient);
    }

    @Override
    public void send(Notification notification){
        super.send(notification);
        sendSMS(notification);
    }

    private void sendSMS(Notification notification) {
        System.out.println("SMS: " + notification.getMessage() + "\nRecepient: " + recepient);
    }
}
