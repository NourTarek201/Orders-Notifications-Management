package com.example.ordernotificationmodule.api.model;

import java.util.*;

public class Compound extends Order {
    private ArrayList<Order> orders = new ArrayList<>();

    public Compound(int customerID, ArrayList<Product> products, ArrayList<Simple> orders) {
        super(customerID, products);
        for(int i = 0; i < orders.size(); i++){
            this.add(orders.get(i));
        }
        double totalShipping = shipping*(orders.size());
        double dividedFees = totalShipping/(orders.size()+1);
        dividedFees = Math.ceil(dividedFees);
        this.shipping = dividedFees;
        for(int i = 0; i < orders.size(); i++){
            orders.get(i).setShipping(dividedFees);
        }
    }

    @Override
    public void add(Order order) {
        orders.add(order);
    }

    @Override
    public void remove(Order order) {
        order.remove(order);
    }

    @Override
    public ArrayList<Order> getChildren() {
        return orders;
    }

    @Override
    public Map<Integer, Integer> totalProducts(){
        Map<Integer, Integer> totalProducts = new HashMap<>();
        for(Product p : products){
            if(totalProducts.containsKey(p.getSerialNo())){
                totalProducts.put(p.getSerialNo(), totalProducts.get(p)+1);
            }
            else {
                totalProducts.put(p.getSerialNo(),1);
            }
        }
        for(Order o : orders){
            for(Product p : o.getProducts()){
                if(totalProducts.containsKey(p.getSerialNo())){
                    totalProducts.put(p.getSerialNo(), totalProducts.get(p.getSerialNo())+1);
                }
                else {
                    totalProducts.put(p.getSerialNo(),1);
                }
            }
        }
        return totalProducts;
    }

}