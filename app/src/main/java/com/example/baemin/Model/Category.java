package com.example.baemin.Model;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String nameType,image;

    public Category(int id, String nameType, String image) {
        this.id = id;
        this.nameType = nameType;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
