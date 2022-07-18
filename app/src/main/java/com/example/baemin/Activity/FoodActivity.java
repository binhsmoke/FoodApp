package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.baemin.Adapter.CategoriesAdapter;
import com.example.baemin.Adapter.FoodAdapter;
import com.example.baemin.DAO.CategoryDao;
import com.example.baemin.DAO.FoodDao;
import com.example.baemin.Interfaces.IClick_Item;
import com.example.baemin.Listener.RecyclerItemClickListener;
import com.example.baemin.Model.Category;
import com.example.baemin.Model.Food;
import com.example.baemin.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    RecyclerView rv;
    FoodAdapter adapter;
    Handler mHandler;
    RecyclerView rcvFood;
    String jsFood;
    TextView tvCategoryName;
    LinearLayout llLoading;
    ImageView gifLoading;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        /*initView();*/
        tvCategoryName=findViewById(R.id.tvCategoryName);
        rcvFood = findViewById(R.id.rcvFood);
        gifLoading = findViewById(R.id.gifLoading);
        llLoading=findViewById(R.id.llLoading);

        Glide.with(this).load(R.drawable.loading).into(new DrawableImageViewTarget(gifLoading));
        mHandler = new Handler();


        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;

        sharedPref = getSharedPreferences("Food", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("KEY_FOOD", null);
        editor.commit();

        sharedPref = getSharedPreferences("Category", Context.MODE_PRIVATE);
        int id= sharedPref.getInt("KEY_ID_CATEGORY",0);
        String name= sharedPref.getString("KEY_NAME_CATEGORY",null);

        tvCategoryName.setText(name);

        sharedPref = getSharedPreferences("Client", Context.MODE_PRIVATE);
        token = sharedPref.getString("KEY_TOKEN",null);

        FoodDao foodDao = new FoodDao(token);
        foodDao.GetFood(id,FoodActivity.this);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref;
                sharedPref = getSharedPreferences("Food", Context.MODE_PRIVATE);
                jsFood = sharedPref.getString("KEY_FOOD",null);
                if(jsFood!=null){
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Food>>(){}.getType();
                    List<Food> alFood = gson.fromJson(jsFood, type);
                    rcvFood.setLayoutManager(new LinearLayoutManager(FoodActivity.this, LinearLayoutManager.VERTICAL, false));
                    FoodAdapter foodAdapter = new FoodAdapter(alFood,FoodActivity.this);
                    rcvFood.setAdapter(foodAdapter);
                    rcvFood.addOnItemTouchListener(new RecyclerItemClickListener(FoodActivity.this, rcvFood, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Food food = alFood.get(position);
                            Intent intent = new Intent(FoodActivity.this, DetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("KEY_FOOD",food);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
                    llLoading.setVisibility(View.GONE);
                    mHandler.removeCallbacks(this);
                }
                else{
                    mHandler.postDelayed(this, 500);
                }
            }
        });
    }

    /*private void initView() {
        rv = findViewById(R.id.food_rv);
        adapter = new FoodAdapter();
        LinearLayoutManager LLM = new LinearLayoutManager(this);
        rv.setLayoutManager(LLM);
        adapter.loadAdapter(getListCategory(),iClick());
        rv.setAdapter(adapter);
    }


    private List<FoodModel> getListCategory() {
        List<FoodModel> rawFoodModel = new ArrayList<>();
        rawFoodModel.add(new FoodModel("Bánh gạo số 1", 1000, 2, R.drawable.fo1));
        rawFoodModel.add(new FoodModel("Bánh gạo số 2", 2000, 3, R.color.teal_200));
        rawFoodModel.add(new FoodModel("Bánh gạo số 3", 3000, 4, R.color.orange));
        rawFoodModel.add(new FoodModel("Bánh gạo số 4", 4000, 5, R.color.green));
        rawFoodModel.add(new FoodModel("Bánh gạo số 1111111", 1000, 2, R.drawable.fo1));
        rawFoodModel.add(new FoodModel("Bánh gạo số 5", 5000, 6, R.color.red));
        rawFoodModel.add(new FoodModel("Bánh gạo số 5", 5000, 6, R.color.black));
        rawFoodModel.add(new FoodModel("Bánh gạo số 1111111111111111111", 1000, 2, R.drawable.fo1));

        return rawFoodModel;
    }

    private IClick_Item iClick() {
        return new IClick_Item() {
            @Override
            public void onClickCartItem(FoodModel foodModel) {
                Intent i = new Intent(FoodActivity.this, DetailActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("my_food", foodModel);
                i.putExtras(b);
                startActivity(i);
            }
        };
    }*/
}
