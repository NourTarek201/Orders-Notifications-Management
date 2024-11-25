package com.example.ordernotificationmodule.api.model;


public class Product {
    private  int serialNo ;
    private String name;
    private String vendor;
    private String category;
    private double price;
    private int count ;


    public Product(int serialNo,String name, String vendor, String category, double price, int count) {
        this.name = name;
        this.vendor = vendor;
        this.category = category;
        this.price = price;
        this.serialNo =serialNo ;
        this.count = count;
    }


    // Getter and setter for 'name'
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for 'vendor'
    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    // Getter and setter for 'category'
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Getter and setter for 'price'
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getter and setter for 'serialNo'
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
