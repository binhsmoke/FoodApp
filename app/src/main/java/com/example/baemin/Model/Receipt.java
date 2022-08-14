package com.example.baemin.Model;

import java.sql.Date;

public class Receipt {
    private String id;
    private int total;
    private String createDate;
    private String note;
    private String isAccepted;
    private int idRestaurant;
    private String phoneClient;
    private String idVouncher;
    private String deliveryAddress;

    public Receipt(String id, int total, String createDate, String note, String isAccepted, int idRestaurant, String phoneClient, String idVouncher, String deliveryAddress) {
        this.id = id;
        this.total = total;
        this.createDate = createDate;
        this.note = note;
        this.isAccepted = isAccepted;
        this.idRestaurant = idRestaurant;
        this.phoneClient = phoneClient;
        this.idVouncher = idVouncher;
        this.deliveryAddress = deliveryAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getPhoneClient() {
        return phoneClient;
    }

    public void setPhoneClient(String phoneClient) {
        this.phoneClient = phoneClient;
    }

    public String getIdVouncher() {
        return idVouncher;
    }

    public void setIdVouncher(String idVouncher) {
        this.idVouncher = idVouncher;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}