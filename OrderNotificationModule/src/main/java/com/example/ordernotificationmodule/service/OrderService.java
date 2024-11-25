package com.example.ordernotificationmodule.service;

import com.example.ordernotificationmodule.NotificationManager;
import com.example.ordernotificationmodule.SharedDatabase;
import com.example.ordernotificationmodule.api.model.*;
import org.springframework.stereotype.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService{

    public void add(Order order){
        SharedDatabase.orders.put(order.getOrderID(),order);
    }

    public boolean delete(int orderID){
        if(SharedDatabase.orders.containsKey(orderID)){
            SharedDatabase.orders.remove(orderID);
            return true;
        }
        return false;
    }

    public Order getOrder(int orderID){
        if(SharedDatabase.orders.containsKey(orderID)){
            return SharedDatabase.orders.get(orderID);
        }
        return null;
    }
    public Order[] getAll(){
        Set<Integer> ids = SharedDatabase.orders.keySet();
        Order[] orders = new Order[ids.size()];
        int i = 0;
        for(int id : ids){
            orders[i] = SharedDatabase.orders.get(id);
            i++;
        }
        return orders;
    }

    public Simple validOrder(Simple order){
        ArrayList<Product> productsOrdered = new ArrayList<>();
        for(int i = 0; i < order.getProducts().size(); i++){
            Product p = SharedDatabase.products.get(order.getProducts().get(i).getSerialNo());
            if(p != null) {
                productsOrdered.add(p);
            }
        }
        if(order.getCustomerID() == 0 || productsOrdered.isEmpty() || productsOrdered.size() < order.getProducts().size()){
            return null;
        }
        order = new Simple(order.getCustomerID(), productsOrdered);
        return order;
    }

//    public Order createOrder(String type, CustomerModel customer, ArrayList<Product> ownProducts, ArrayList<Order> friendsOrders) {
//        // Check if the customer exists
//        if (!isCustomerExist(customer)) {
//            return null;
//        }
//        if (type.equals("simple")) {
//            return addSimpleOrder(customer, ownProducts);
//        } else {
//            return addCompoundOrder(customer, ownProducts, friendsOrders);
//        }
//    }
//    private boolean isCustomerExist(CustomerModel customer) {
//        return SharedDatabase.customers.containsKey(customer.getId());
//    }
//
//    public double getTotalAmount(Order order) {
//        double totalAmount = 0.0;
//        for (Product product : order.getProducts()) {
//            totalAmount += product.getPrice();
//        }
//        return totalAmount;
//    }
//
//    public void deductBalance(CustomerModel customer,double Amount){
//        customer.setBalance(customer.getBalance()-Amount);
//
//    }
//
//
//    public Order addSimpleOrder(CustomerModel customer, ArrayList<Product> ownProducts) {
//        Order simpleOrder = new Simple(ownProducts, customer);
//
//        orders.add(simpleOrder);
//        customer.setBalance(customer.getBalance()-getTotalAmount(simpleOrder));
//        return simpleOrder;
//    }
//    public Order addCompoundOrder(CustomerModel customer, ArrayList<Product> ownProducts, ArrayList<Order> friendsOrders) {
//
//        ArrayList<Product> friendsProducts = extractProductsFromFriendsOrders(friendsOrders);
//
//        ArrayList<Product> totalProducts = new ArrayList<>();
//        totalProducts.addAll(ownProducts);
//        totalProducts.addAll(friendsProducts);
//
//        Order compoundOrder = new Compound(totalProducts, friendsOrders);
//        orders.add(compoundOrder);
//
//        deductBalance(customer, getTotalAmount(compoundOrder));
//        for (Order friendOrder : friendsOrders) {
//            deductBalance(friendOrder.getCustomer(), getTotalAmount(friendOrder));
//        }
//        return compoundOrder;
//    }
//
//    public ArrayList<Product> extractProductsFromFriendsOrders(ArrayList<Order> friendsOrders) {
//        ArrayList<Product> friendsProducts = new ArrayList<>();
//        for (Order friendOrder : friendsOrders) {
//            friendsProducts.addAll(friendOrder.getProducts());
//        }
//        return friendsProducts;
//    }
//
//    public Simple getSimpleOrder(int orderId) {
//        for (Order order : orders) {
//            if (order.getOrderID() == orderId && order instanceof Simple) {
//                return (Simple) order;
//            }
//        }
//        return null;
//    }
//
//
//
//    public Compound getCompoundOrder(int orderId) {
//        for (Order order : orders) {
//            if (order.getOrderID() == orderId && order instanceof Compound) {
//                return (Compound) order;
//            }
//        }
//        return null;
//    }
//
//    public Order getOrderDetails(int orderId) {
//        for (Order order : orders) {
//            if (order.getOrderID() == orderId) {
//                if (order instanceof Simple) {
//                    getSimpleOrder(orderId);
//                } else if (order instanceof Compound) {
//                    getCompoundOrder(orderId);
//                }
//            }
//        }
//        return null;
//    }
}
