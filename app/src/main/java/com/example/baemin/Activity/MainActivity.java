package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.baemin.Adapter.CategoriesAdapter;
import com.example.baemin.DAO.CategoryDao;
import com.example.baemin.Listener.RecyclerItemClickListener;
import com.example.baemin.Model.Category;
import com.example.baemin.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter res_adapter, food_adapter;
    private RecyclerView rvResList, rvFoodList;
    String jsCategory;
    Runnable mRunnable;
    Handler mHandler;
    RecyclerView rcvCategory;
    LinearLayout llLoading;
    ImageView gifLoading;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*add_to_rvResList();
        add_to_rvFoodList();*/
        gifLoading = findViewById(R.id.gifLoading);
        Glide.with(this).load(R.drawable.loading).into(new DrawableImageViewTarget(gifLoading));
        llLoading = findViewById(R.id.llLoading);
        rcvCategory = findViewById(R.id.rcvCategory);
        mHandler = new Handler();

        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("Client", Context.MODE_PRIVATE);
        token = sharedPref.getString("KEY_TOKEN",null);

        CategoryDao categoryDao = new CategoryDao(token);
        categoryDao.GetCategory(MainActivity.this);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref;
                sharedPref = getSharedPreferences("Category", Context.MODE_PRIVATE);
                jsCategory = sharedPref.getString("KEY_CATEGORY",null);
                if(jsCategory!=null){
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Category>>(){}.getType();
                    List<Category> alCategory = gson.fromJson(jsCategory, type);
                    rcvCategory.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    CategoriesAdapter categoriesAdapter = new CategoriesAdapter(MainActivity.this);
                    categoriesAdapter.loadAdapter(alCategory);
                    rcvCategory.setAdapter(categoriesAdapter);
                    llLoading.setVisibility(View.GONE);
                    rcvCategory.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, rcvCategory, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            SharedPreferences sharedPref;
                            SharedPreferences.Editor editor;
                            sharedPref = getSharedPreferences("Category", Context.MODE_PRIVATE);
                            editor = sharedPref.edit();
                            editor.putInt("KEY_ID_CATEGORY", alCategory.get(position).getId());
                            editor.putString("KEY_NAME_CATEGORY", alCategory.get(position).getNameType());
                            editor.commit();
                            startActivity(new Intent(MainActivity.this, FoodActivity.class));
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
                    mHandler.removeCallbacks(this);
                }
                else{
                    mHandler.postDelayed(this, 500);
                }
            }
        });

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