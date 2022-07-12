package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.baemin.Adapter.CartAdapter;
import com.example.baemin.Adapter.FoodAdapter;
import com.example.baemin.Interfaces.IClick_Item;
import com.example.baemin.Model.CategoriesModel;
import com.example.baemin.Model.FoodModel;
import com.example.baemin.R;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    RecyclerView rv;
    FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initView();
    }

    private void initView() {
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
        rawFoodModel.add(new FoodModel("Bánh gạo số 2", 2000, 3, R.drawable.ic_vietflag));
        rawFoodModel.add(new FoodModel("Bánh gạo số 3", 3000, 4, R.drawable.ic_baseline_cancel));
        rawFoodModel.add(new FoodModel("Bánh gạo số 4", 4000, 5, R.drawable.ic_baseline_person_24));
        rawFoodModel.add(new FoodModel("Bánh gạo số 5", 5000, 6, R.drawable.fo1));

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
    }
}