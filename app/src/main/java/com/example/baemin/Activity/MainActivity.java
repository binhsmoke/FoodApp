package com.example.baemin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baemin.Adapter.CategoriesAdapter;
import com.example.baemin.Interfaces.IClick_Item;
import com.example.baemin.Model.CategoriesModel;
import com.example.baemin.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addRvCat();


    } //end of main

    //- Categories
    private void addRvCat() {
        View viewCat = findViewById(R.id.cat_layout);
        RecyclerView rvCategories = viewCat.findViewById(R.id.cat_rv);
        CategoriesAdapter adapterCategories = new CategoriesAdapter(this);
        GridLayoutManager GM = new GridLayoutManager(this, 4);
        rvCategories.setLayoutManager(GM);
        List<CategoriesModel> rawCatModel = new ArrayList<>();
        rawCatModel.add(new CategoriesModel("Bánh gạo", R.drawable.cat1));
        rawCatModel.add(new CategoriesModel("Buổi trưa", R.drawable.cat1));
        rawCatModel.add(new CategoriesModel("Ramen", R.drawable.cat1));
        rawCatModel.add(new CategoriesModel("Canh Hàn Quôc", R.drawable.cat1));
        rawCatModel.add(new CategoriesModel("Gà rán sốt", R.drawable.cat1));
        rawCatModel.add(new CategoriesModel("Gà rán không xương", R.drawable.cat1));
        rawCatModel.add(new CategoriesModel("Gà rán không xương phô mai", R.drawable.cat1));
        rawCatModel.add(new CategoriesModel("Salad", R.drawable.cat1));
        rawCatModel.add(new CategoriesModel("Snack", R.drawable.cat1));
        rawCatModel.add(new CategoriesModel("Nước ngọt - Bia", R.drawable.cat1));
        //
        adapterCategories.loadAdapter(rawCatModel);
        rvCategories.setAdapter(adapterCategories);

    }



    //di den Gio Hang tam thoi = Floating button
    public void gotoCartActivity(View view) {
        startActivity(new Intent(MainActivity.this, CartActivity.class));
    }    //di den Gio Hang tam thoi = Floating button
    public void gotoFoodActivity(View view) {
        startActivity(new Intent(MainActivity.this, FoodActivity.class));
    }
}