package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.baemin.Adapter.FoodAdapter;
import com.example.baemin.Adapter.RestaurantAdapter;
import com.example.baemin.Model.FoodModel;
import com.example.baemin.Model.RestaurantModel;
import com.example.baemin.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter res_adapter, food_adapter;
    private RecyclerView rvResList, rvFoodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*add_to_rvResList();
        add_to_rvFoodList();*/
    }



    /*private void add_to_rvResList() {
        LinearLayoutManager LLM = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvResList = findViewById(R.id.rvRestaurant);
        rvResList.setLayoutManager(LLM);

        ArrayList<RestaurantModel> myResList = new ArrayList<>();
        myResList.add(new RestaurantModel("Res1", "res1p"));
        myResList.add(new RestaurantModel("Res2", "res2p"));
        myResList.add(new RestaurantModel("Res3", "res3p"));
        myResList.add(new RestaurantModel("Res4", "res4p"));
        myResList.add(new RestaurantModel("Res5", "res5p"));

        res_adapter = new RestaurantAdapter(myResList);
        rvResList.setAdapter(res_adapter);
    }
    //
    private void add_to_rvFoodList() {
        LinearLayoutManager LLM = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFoodList = findViewById(R.id.rvFood);
        rvFoodList.setLayoutManager(LLM);

        ArrayList<FoodModel> myFoodList = new ArrayList<>();
        myFoodList.add(new FoodModel("Fry Rice", "fo1", 35.000, true));
        myFoodList.add(new FoodModel("VietNamese Noodle", "fo2", 50.000, true));
        myFoodList.add(new FoodModel("Dim Sum", "fo3", 20.000, true));
        myFoodList.add(new FoodModel("Salad", "fo4", 30.000, true));
        myFoodList.add(new FoodModel("Spring Rolls", "fo5", 15.000, true));
        myFoodList.add(new FoodModel("Beefsteak", "fo6", 200.000, true));

        food_adapter = new FoodAdapter(myFoodList);
        rvFoodList.setAdapter(food_adapter);
    }*/


}