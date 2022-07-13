package com.example.baemin.Model;

public class Food {
    int id;
    String nameFood;
    int priceFood;
    String image;
    String isAvailable;
    int idType;
    int orderTime;
    String ingredients;

    public Food(int id, String nameFood, int priceFood, String image, String isAvailable, int idType, int orderTime, String ingredients) {
        this.id = id;
        this.nameFood = nameFood;
        this.priceFood = priceFood;
        this.image = image;
        this.isAvailable = isAvailable;
        this.idType = idType;
        this.orderTime = orderTime;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public int getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(int priceFood) {
        this.priceFood = priceFood;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
