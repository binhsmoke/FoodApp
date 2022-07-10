package com.example.baemin.Model;

import java.io.Serializable;

public class CartModel implements Serializable {
    private String cart_name;
    private int cart_price;
    private int cart_quantity;
    private int cart_pic;

    public CartModel(String cart_name, int cart_price, int cart_quantity, int cart_pic) {
        this.cart_name = cart_name;
        this.cart_price = cart_price;
        this.cart_quantity = cart_quantity;
        this.cart_pic = cart_pic;
    }

    public String getCart_name() {
        return cart_name;
    }

    public void setCart_name(String cart_name) {
        this.cart_name = cart_name;
    }

    public int getCart_price() {
        return cart_price;
    }

    public void setCart_price(int cart_price) {
        this.cart_price = cart_price;
    }

    public int getCart_quantity() {
        return cart_quantity;
    }

    public void setCart_quantity(int cart_quantity) {
        this.cart_quantity = cart_quantity;
    }

    public int getCart_pic() {
        return cart_pic;
    }

    public void setCart_pic(int cart_pic) {
        this.cart_pic = cart_pic;
    }
}
