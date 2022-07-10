package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.baemin.R;

public class StartActivity extends AppCompatActivity {
    LinearLayout buttonStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        buttonStart=findViewById(R.id.llStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
            }
        });
    }
}