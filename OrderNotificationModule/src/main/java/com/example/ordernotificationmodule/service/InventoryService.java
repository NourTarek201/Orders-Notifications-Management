package com.example.ordernotificationmodule.service;

import com.example.ordernotificationmodule.SharedDatabase;
import com.example.ordernotificationmodule.api.model.*;
import org.springframework.stereotype.*;
import java.util.*;

@Service
public class InventoryService {

    public InventoryService(){
        SharedDatabase.products.put(1, new Product(1, "Tomato", "Vendor1", "Category1", 2.5, 10));
        SharedDatabase.products.put(2, new Product(2, "Potato", "Vendor2", "Category2", 1.0, 20));
        SharedDatabase.products.put(3, new Product(3, "Carrot", "Vendor1", "Category1", 1.5, 15));
        SharedDatabase.products.put(4, new Product(4, "Apple", "Vendor3", "Category3", 3.0, 8));
        SharedDatabase.products.put(5, new Product(5, "Banana", "Vendor2", "Category3", 1.2, 25));
        SharedDatabase.products.put(6, new Product(6, "Orange", "Vendor3", "Category2", 2.0, 12));
        SharedDatabase.products.put(7, new Product(7, "Broccoli", "Vendor1", "Category1", 2.8, 18));
        SharedDatabase.products.put(8, new Product(8, "Strawberry", "Vendor4", "Category3", 4.0, 30));
        SharedDatabase.products.put(9, new Product(9, "Cucumber", "Vendor2", "Category2", 1.2, 22));
        SharedDatabase.products.put(10, new Product(10, "Lettuce", "Vendor4", "Category1", 2.0, 15));

    }

    public int getRemainProducts(String category){
        int remain=0;
        for (Map.Entry<Integer, Product> element : SharedDatabase.products.entrySet()) {
            if(element.getValue().getCategory().equalsIgnoreCase(category)){
                remain += element.getValue().getCount();
            }
        }

        return remain;
    }




    public boolean newProduct(Product product){
        if(SharedDatabase.products.containsKey(product.getSerialNo())&&
                !SharedDatabase.products.get(product.getSerialNo()).getName().equals(product.getName())){
            return false;
        }

        if(product.getName() != null && product.getVendor() != null &&
                product.getPrice() != 0 && product.getCategory() != null ){

            if(SharedDatabase.products.containsKey(product.getSerialNo())){
                product.setCount(SharedDatabase.products.get(product.getSerialNo()).getCount()+
                        product.getCount());
            }

            SharedDatabase.products.put(product.getSerialNo(), product);
            return true;
        }

        return false;
    }

    public ArrayList<Product> getAvailableProducts(){
        return new ArrayList<>(SharedDatabase.products.values());
    }

    public Product getProduct(int serialNo){
        if(SharedDatabase.products.get(serialNo) == null){
            return null;
        }
        return SharedDatabase.products.get(serialNo);
    }

    public void takeItem(int serialNo, int count){
        Product p = this.getProduct(serialNo);
        p.setCount(p.getCount()-count);
    }

    public void addItem(int serialNo, int count){
        Product p = this.getProduct(serialNo);
        p.setCount(p.getCount()+count);
    }

    public boolean enoughItems(Map<Integer, Integer> items){
        for (Map.Entry<Integer, Integer> item : items.entrySet()){
            Product p = SharedDatabase.products.get(item.getKey());
            if (p.getCount() - item.getValue() < 0){
                return false;
            }
        }
        return true;
    }

}

