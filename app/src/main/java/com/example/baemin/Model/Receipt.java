package com.example.baemin.Model;

import java.sql.Date;

public class Receipt {
    private String id;
    private int total;
    private String dateTime;
    private String note;
    private String isAccepted;
    private String deliveryAddress;
    private int idRestaurant;
    private String phoneClient;
    private String idVouncher;

    public Receipt(String id, int total, String dateTime, String note, String isAccepted, String deliveryAddress, int idRestaurant, String phoneClient, String idVouncher) {
        this.id = id;
        this.total = total;
        this.dateTime = dateTime;
        this.note = note;
        this.isAccepted = isAccepted;
        this.deliveryAddress = deliveryAddress;
        this.idRestaurant = idRestaurant;
        this.phoneClient = phoneClient;
        this.idVouncher = idVouncher;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
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
}