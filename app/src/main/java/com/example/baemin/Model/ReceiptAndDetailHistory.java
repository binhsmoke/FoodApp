package com.example.baemin.Model;

import java.util.ArrayList;

public class ReceiptAndDetailHistory {
    private  Receipt receipt;
    private ArrayList<Cart> cartList;

    public ReceiptAndDetailHistory(Receipt receipt, ArrayList<Cart> cartList) {
        this.receipt = receipt;
        this.cartList = cartList;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public ArrayList<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(ArrayList<Cart> cartList) {
        this.cartList = cartList;
    }
}
