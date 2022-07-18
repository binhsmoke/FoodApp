package com.example.baemin.Model;

public class Cart {
    private int idFood, quantity, price;
    private String image, name;

    public Cart(int idFood, int quantity, int price, String image, String name) {
        this.idFood = idFood;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.name = name;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
