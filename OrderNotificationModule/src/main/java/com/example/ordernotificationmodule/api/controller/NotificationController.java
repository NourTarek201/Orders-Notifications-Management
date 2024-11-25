package com.example.ordernotificationmodule.api.controller;

import com.example.ordernotificationmodule.service.CustomerService;
import com.example.ordernotificationmodule.service.NotificationService;
import com.example.ordernotificationmodule.api.model.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;


@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @Autowired
    CustomerService customerService;
    PlacementFactory placementFactory = new PlacementFactory();
    ShipmentFactory shipmentFactory = new ShipmentFactory();

    @GetMapping("/getall")
    public Notification[] getAll(){
        return notificationService.getAll();
    }

    @GetMapping("/fill")
    public boolean fill(){
        // for testing only
        ArrayList<String> p1 = new ArrayList<>();
        p1.add("Nour");
        p1.add("0001");
        ArrayList<String> p2 = new ArrayList<>();
        p2.add("Jung Kook");
        p2.add("0002");
        Template confirmation = placementFactory.createTemplate("Confirmation",p1);
        Template delivery = shipmentFactory.createTemplate("Delivery", p2);
        Template shipment = shipmentFactory.createTemplate("Shipment",p2);
        Channel c1 = new Channel();
        c1 = new EmailDecorator(c1, "nour@email.com");
        Channel c2 = new Channel();
        c2 = new EmailDecorator(c2, "jungkook@email.com");
        c2 = new SMSDecorator(c2, "09011997");
        Channel c3 = new Channel();
        c3 = new EmailDecorator(c3, "jungkook@email.com");
        Notification nour = new Notification(confirmation,c1);
        Notification jungkook = new Notification(delivery,c2);
        Notification jungkook2 = new Notification(shipment, c3);
        notificationService.add(nour);
        notificationService.add(jungkook);
        notificationService.add(jungkook2);
        return true;
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, String>> getStatistics(){
        Map<String, String> body = new HashMap<>();
        body.put("most notified email/phone number", notificationService.getMostNotified());
        body.put("most sent template type", notificationService.getMostSentTemplate());
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PostMapping("/create")
    public Notification createNotification(String type, int customerID, int orderID){
        CustomerModel customer = customerService.getCustomer(customerID);
        ArrayList<String> placeholders = new ArrayList<>();
        placeholders.add(customer.getName());
        placeholders.add(Integer.toString(orderID));
        Template t = placementFactory.createTemplate(type,placeholders);
        if (t == null){
            t = shipmentFactory.createTemplate(type,placeholders);
        }
        Channel c = new Channel();
        if(customer.isEmailEnabled()){
            c = new EmailDecorator(c, customer.getEmail());
        }
        if (customer.isSmsEnabled()){
            c = new SMSDecorator(c, customer.getPhoneNumber());
        }
        Notification n = new Notification(t,c);
        notificationService.add(n);
        return n;
    }

}


