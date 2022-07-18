package com.example.baemin.Model;

import java.io.Serializable;

public class FoodModel implements Serializable {
    private String food_name;
    private int food_price;
    private int food_quantity;
    private int food_pic;

    public FoodModel(String food_name, int food_price, int food_quantity, int food_pic) {
        this.food_name = food_name;
        this.food_price = food_price;
        this.food_quantity = food_quantity;
        this.food_pic = food_pic;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getFood_price() {
        return food_price;
    }

    public void setFood_price(int food_price) {
        this.food_price = food_price;
    }

    public int getFood_quantity() {
        return food_quantity;
    }

    public void setFood_quantity(int food_quantity) {
        this.food_quantity = food_quantity;
    }

    public int getFood_pic() {
        return food_pic;
    }

    public void setFood_pic(int food_pic) {
        this.food_pic = food_pic;
    }
}
