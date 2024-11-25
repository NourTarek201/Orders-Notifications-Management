package com.example.ordernotificationmodule.api.model;
import java.util.*;

public class Simple extends Order{
    public Simple(int customerID, ArrayList<Product> products) {
        super(customerID, products);
    }

    @Override
    public Map<Integer, Integer> totalProducts(){
        Map<Integer, Integer> totalProducts = new HashMap<>();
        for(Product p : products){
            if(totalProducts.containsKey(p.getSerialNo())){
                totalProducts.put(p.getSerialNo(), totalProducts.get(p.getSerialNo())+1);
            }
            else {
                totalProducts.put(p.getSerialNo(),1);
            }
        }
        return totalProducts;
    }

    @Override
    public void add(Order order) {

    }

    @Override
    public void remove(Order order) {

    }

    @Override
    public ArrayList<Order> getChildren() {
        return new ArrayList<>(0);
    }

}
