package com.example.baemin.Model;

import java.io.Serializable;

public class CategoriesModel implements Serializable {
    private String cat_name;
    private int cat_pic;

    public CategoriesModel(String cat_name, int pic) {
        this.cat_name = cat_name;
        this.cat_pic = pic;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public int getCat_pic() {
        return cat_pic;
    }

    public void setCat_pic(int cat_pic) {
        this.cat_pic = cat_pic;
    }
}
