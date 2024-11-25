package com.example.ordernotificationmodule.api.controller;

import com.example.ordernotificationmodule.service.CustomerService;
import com.example.ordernotificationmodule.service.InventoryService;
import com.example.ordernotificationmodule.api.model.*;
import com.example.ordernotificationmodule.service.OrderService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.*;


@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    InventoryService inventoryService;
    @Autowired
    CustomerService customerService;
    @Autowired
    NotificationController notificationController;
    @Autowired
    CustomerController customerController;

    @PostMapping("/placesimple")
    public Response placeSimpleOrder(@RequestBody SimpleWrapper wrapper){
        Response res = customerController.check(wrapper.getId(), wrapper.getPassword());
        if(!res.getStatus()){
            return res;
        }
        return simpleOrder(wrapper.getOrder());
    }

    @PostMapping("/placecompound")
    public Response placeCompoundOrder(@RequestBody CompoundWrapper wrapper){
        Response res = customerController.check(wrapper.getId(), wrapper.getPassword());
        if(!res.getStatus()){
            return res;
        }
        for(Order o : wrapper.getOrder().getChildren()){
            if(customerService.getCustomer(o.getCustomerID()) == null){
                res.setStatus(false);
                res.setResult("Invalid ID for friend's order");
                return res;
            }
        }
        return compoundOrder(wrapper.getOrder());
    }

    @PostMapping("/simple")
    public Response simpleOrder(@RequestBody Simple order){
        Response res = new Response();
        order = orderService.validOrder(order);
        if(order != null) {
            if (inventoryService.enoughItems(order.totalProducts())){
                if (customerService.deductAmount(order.getCustomerID(), order.getAmount())) {
                    orderService.add(order);
                    for(Map.Entry<Integer, Integer> item : order.totalProducts().entrySet()){
                        inventoryService.takeItem(item.getKey(), item.getValue());
                    }
                    Notification n = notificationController.createNotification("Confirmation", order.getCustomerID(), order.getOrderID());
                    res.setStatus(true);
                    res.setResult(n.getMessage());
                    return res;
                }
                res.setStatus(false);
                res.setResult("Insufficient balance. Order can't be placed.");
                return res;
            }
            res.setStatus(false);
            res.setResult("Not enough items in inventory. Order can't be placed.");
            return res;
        }
        res.setStatus(false);
        res.setResult("An error occurred. Order can't be placed.");
        return res;
    }

    @PostMapping("/compound")
    public Response compoundOrder(@RequestBody Compound order){
        Response res = new Response();
        Simple customerOrder = new Simple(order.getCustomerID(), order.getProducts());
        customerOrder = orderService.validOrder(customerOrder);
        if(customerOrder != null) {
            ArrayList<Simple> friendsOrders = new ArrayList<>();
            for (Order o : order.getChildren()){
                Simple friendOrder = new Simple(o.getCustomerID(), o.getProducts());
                friendOrder = orderService.validOrder(friendOrder);
                if(friendOrder == null){
                    res.setStatus(false);
                    res.setResult("An error occurred. Order can't be placed.");
                    return res;
                }
                friendsOrders.add(friendOrder);
            }
            order = new Compound(customerOrder.getCustomerID(), customerOrder.getProducts(), friendsOrders);
            if(inventoryService.enoughItems(order.totalProducts())) {
                if (customerService.getCustomer(order.getCustomerID()).getBalance() - order.getAmount() >= 0) {
                    for (Order o : order.getChildren()) {
                        if (customerService.getCustomer(o.getCustomerID()).getBalance() - o.getAmount() >= 0) {
                            continue;
                        } else {
                            res.setStatus(false);
                            res.setResult("Insufficient balance. Order can't be placed.");
                            return res;
                        }
                    }
                    customerService.deductAmount(order.getCustomerID(), order.getAmount());
                    for (Order o : order.getChildren()) {
                        customerService.deductAmount(o.getCustomerID(), o.getAmount());
                    }
                    orderService.add(order);
                    for(Map.Entry<Integer, Integer> item : order.totalProducts().entrySet()){
                        inventoryService.takeItem(item.getKey(), item.getValue());
                    }
                    Notification n = notificationController.createNotification("Confirmation", order.getCustomerID(), order.getOrderID());
                    res.setStatus(true);
                    res.setResult(n.getMessage());
                    return res;

                }
                res.setStatus(false);
                res.setResult("Insufficient balance. Order can't be placed.");
                return res;
            }
            res.setStatus(false);
            res.setResult("An error occurred. Order can't be placed.");
            return res;

        }
        res.setStatus(false);
        res.setResult("An error occurred. Order can't be placed.");
        return res;
    }

    @GetMapping("/{id}/details")
    public Response getOrderDetails(@PathVariable("id") int id){
        Response res = new Response();
        Order order = orderService.getOrder(id);
        if(order != null){
            res.setStatus(true);
            res.setResult(order);
            return res;
        }
        res.setStatus(false);
        res.setResult("No order with such id exists.");
        return res;
    }

    @GetMapping("/{id}/ship")
    public Response shipOrder(@PathVariable("id") int id){
        Response res = new Response();
        Order order = orderService.getOrder(id);
        if(order != null){
            if(!order.getOrderStatus().equals("Pending")){
                res.setStatus(false);
                String message = String.format("Order can't be shipped. It has already been %s.", order.getOrderStatus());
                res.setResult(message);
                return res;
            }
            if (customerService.getCustomer(order.getCustomerID()).getBalance() - order.getShipping() >= 0) {
                for (Order o : order.getChildren()) {
                    if (customerService.getCustomer(o.getCustomerID()).getBalance() - o.getShipping() >= 0) {
                        continue;
                    } else {
                        res.setStatus(false);
                        res.setResult("Insufficient balance for one of friends. Order can't be shipped yet.");
                        return res;
                    }
                }
                customerService.deductAmount(order.getCustomerID(), order.getShipping());
                order.setStatus(OrderStatus.SHIPPED);
                for (Order o : order.getChildren()) {
                    customerService.deductAmount(o.getCustomerID(), o.getShipping());
                    o.setStatus(OrderStatus.SHIPPED);
                }
                Notification n = notificationController.createNotification("Shipment", order.getCustomerID(), order.getOrderID());
                res.setStatus(true);
                res.setResult(n.getMessage());
                return res;

            }
            res.setStatus(false);
            res.setResult("Insufficient balance. Order can't be shipped yet.");
            return res;
        }
        res.setStatus(false);
        res.setResult("No order with such id exists.");
        return res;
    }


    // BONUS PART

    @GetMapping("/{id}/cancelorder")
    public Response cancelOrder(@PathVariable("id") int id){
        Response res = new Response();
        Order order = orderService.getOrder(id);
        if(order != null){
            if(!order.getOrderStatus().equals("Pending")){
                res.setStatus(false);
                String message = String.format("Order can't be cancelled. It has already been %s.", order.getOrderStatus());
                res.setResult(message);
                return res;
            }
            LocalDateTime dateTime = order.getTimestamp();
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime startOfDateTime = dateTime.toLocalDate().atStartOfDay();
            LocalDateTime startOfCurrentDateTime = currentDateTime.toLocalDate().atStartOfDay();
            long daysDifference = ChronoUnit.DAYS.between(startOfDateTime, startOfCurrentDateTime);
            if(daysDifference < 2) {
                customerService.addAmount(order.getCustomerID(), order.getAmount());
                order.setStatus(OrderStatus.CANCELED);
                for (Order o : order.getChildren()) {
                    customerService.addAmount(o.getCustomerID(), o.getAmount());
                    o.setStatus(OrderStatus.CANCELED);
                }
                for(Map.Entry<Integer, Integer> item : order.totalProducts().entrySet()){
                    inventoryService.addItem(item.getKey(), item.getValue());
                }
                orderService.delete(order.getOrderID());
                res.setStatus(true);
                res.setResult("Order has been cancelled successfully.");
                return res;
            }
            res.setStatus(false);
            res.setResult("Shipping can't be canceled after two days.");
            return res;
        }
        res.setStatus(false);
        res.setResult("No order with such id exists.");
        return res;
    }

    @GetMapping("/{id}/cancelshipping")
    public Response cancelShipping(@PathVariable("id") int id){
        Response res = new Response();
        Order order = orderService.getOrder(id);
        if(order != null){
            if(order.getOrderStatus().equals("Shipped")) {
                LocalDateTime dateTime = order.getTimestamp();
                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalDateTime startOfDateTime = dateTime.toLocalDate().atStartOfDay();
                LocalDateTime startOfCurrentDateTime = currentDateTime.toLocalDate().atStartOfDay();
                long daysDifference = ChronoUnit.DAYS.between(startOfDateTime, startOfCurrentDateTime);
                if(daysDifference < 2) {
                    customerService.addAmount(order.getCustomerID(), order.getShipping());
                    order.setStatus(OrderStatus.PENDING);
                    for (Order o : order.getChildren()) {
                        customerService.addAmount(o.getCustomerID(), o.getShipping());
                        o.setStatus(OrderStatus.PENDING);
                    }
                    res.setStatus(true);
                    String message = String.format("Shipping for Order #%s has been cancelled.", order.getOrderID());
                    res.setResult(message);
                    return res;
                }
                res.setStatus(false);
                res.setResult("Shipping can't be canceled after two days.");
                return res;
            }
            res.setStatus(false);
            String message = String.format("Shipping of order can't be cancelled. It is %s.", order.getOrderStatus());
            res.setResult(message);
            return res;
        }
        res.setStatus(false);
        res.setResult("No order with such id exists.");
        return res;
    }

}
