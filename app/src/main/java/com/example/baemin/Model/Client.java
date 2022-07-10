package com.example.baemin.Model;

public class Client {
    private String phone, password, name, address;
    private int orderTime;

    public Client(String phone, String password, String name, String address, int orderTime) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.address = address;
        this.orderTime = orderTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
    }
}
