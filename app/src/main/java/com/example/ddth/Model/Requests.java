package com.example.ddth.Model;

import java.util.List;

public class Requests {
    private String phone;
    private String address;
    private String name;
    private int Total;
    private String Status;
    private List<Order> foods; // list of Food Order
    private transient String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Requests() {
    }

    public int getTotal() {
        return Total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Requests(String phone, String address, String name, int total, List<Order> foods) {
        this.phone = phone;
        this.address = address;
        this.name = name;
        Total = total;
        this.foods = foods;
        this.Status = "0"; // default 0, 0 : Order , 1 ; Shipping , 2 : Shipped
    }

    public String getPhone() {
        return phone;
    }
}
