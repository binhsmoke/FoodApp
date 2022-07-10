package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baemin.Model.CartModel;
import com.example.baemin.R;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bun = getIntent().getExtras();
        if (bun == null) {
            Toast.makeText(this, "Quantity wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        CartModel cartModel = (CartModel) bun.get("my_cartModel");


    }
}