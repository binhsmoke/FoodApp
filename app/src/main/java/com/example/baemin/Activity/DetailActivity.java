package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baemin.R;

public class DetailActivity extends AppCompatActivity {

    ImageView imgPic;
    TextView tvName, tvPrice, tvQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imgPic = findViewById(R.id.detail_pic);
        tvName = findViewById(R.id.detail_name);
        tvPrice = findViewById(R.id.detail_price);
        tvQuantity = findViewById(R.id.detail_quantity);
        Bundle bun = getIntent().getExtras();
        /*FoodModel foodModel = (FoodModel) bun.get("my_food");
        imgPic.setImageResource(foodModel.getFood_pic());
        tvName.setText(foodModel.getFood_name());
        tvPrice.setText(String.valueOf(foodModel.getFood_price()));
        tvQuantity.setText(String.valueOf(foodModel.getFood_quantity()));
*/
    }

}