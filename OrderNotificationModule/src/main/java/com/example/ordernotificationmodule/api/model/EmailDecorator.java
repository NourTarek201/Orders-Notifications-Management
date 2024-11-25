package com.example.ordernotificationmodule.api.model;

public class EmailDecorator extends ChannelDecorator{
    public EmailDecorator(Channel channel, String recepient) {
        super(channel, recepient);
    }

    @Override
    public void send(Notification notification){
        super.send(notification);
        sendEmail(notification);
    }

    private void sendEmail(Notification notification) {
        System.out.println("Email: " + notification.getMessage() + "\nRecepient: " + recepient);
    }
}
