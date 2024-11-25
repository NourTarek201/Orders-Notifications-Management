package com.example.ordernotificationmodule.api.model;


import java.time.LocalDateTime;
import java.util.*;

public abstract class Order {
    protected static int counter = 100;
    protected int orderID;
    protected int customerID;
    protected OrderStatus status;
    protected double amount;
    protected LocalDateTime timestamp;

    protected double shipping = 100;
    protected  ArrayList<Product> products = new ArrayList<>();


    public Order(int customerID, ArrayList<Product> products){
        this.customerID = customerID;
        this.orderID = counter;
        this.status = OrderStatus.PENDING;
        timestamp = LocalDateTime.now();
        this.products = products;
        this.amount = 0;
        for(int i = 0; i < products.size(); i++){
            amount = amount + products.get(i).getPrice();
        }
        counter++;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getOrderStatus() {
        return status.toString();
    }

    public double getAmount() {
        return amount;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public double getShipping() {
        return shipping;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public abstract void add(Order order);
    public abstract void remove(Order order);
    public abstract ArrayList<Order> getChildren();

    protected void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public abstract Map<Integer, Integer> totalProducts();
}
