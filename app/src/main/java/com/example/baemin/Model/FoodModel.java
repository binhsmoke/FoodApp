package com.example.baemin.Model;

public class FoodModel {
    private String nameFood;
    private String picFood;
    private Double priceFood;
    private boolean isAvailableFood;
    private int quantityFood;

    public FoodModel(String nameFood, String picFood, Double priceFood, boolean isAvailableFood) {
        this.nameFood = nameFood;
        this.picFood = picFood;
        this.priceFood = priceFood;
        this.isAvailableFood = isAvailableFood;
    }

    public FoodModel(String nameFood, String picFood, Double priceFood, boolean isAvailableFood, int quantityFood) {
        this.nameFood = nameFood;
        this.picFood = picFood;
        this.priceFood = priceFood;
        this.isAvailableFood = isAvailableFood;
        this.quantityFood = quantityFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getPicFood() {
        return picFood;
    }

    public void setPicFood(String picFood) {
        this.picFood = picFood;
    }

    public Double getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(Double priceFood) {
        this.priceFood = priceFood;
    }

    public boolean isAvailableFood() {
        return isAvailableFood;
    }

    public void setAvailableFood(boolean availableFood) {
        isAvailableFood = availableFood;
    }

    public int getQuantityFood() {
        return quantityFood;
    }

    public void setQuantityFood(int quantityFood) {
        this.quantityFood = quantityFood;
    }
}
