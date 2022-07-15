package com.example.baemin.Model;

public class ReceiptDetail {
    private int idFood, idReceipt, quantity, price;

    public ReceiptDetail(int idFood, int idReceipt, int quantity, int price) {
        this.idFood = idFood;
        this.idReceipt = idReceipt;
        this.quantity = quantity;
        this.price = price;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public int getIdReceipt() {
        return idReceipt;
    }

    public void setIdReceipt(int idReceipt) {
        this.idReceipt = idReceipt;
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
}
