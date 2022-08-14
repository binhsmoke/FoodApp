package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.baemin.R;

public class Client_Info_ChangePWD_Activity extends AppCompatActivity {
    TextView tvMyTransaction;
    TextView tvClientName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        tvClientName=findViewById(R.id.tvName);
        tvMyTransaction = findViewById(R.id.tvMyTransactions);
        SharedPreferences sharedPreferences = getSharedPreferences("Client",MODE_PRIVATE);
        String name= sharedPreferences.getString("KEY_NAME",null);
        tvClientName.setText(name);
        tvMyTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Client_Info_ChangePWD_Activity.this,Client_Transaction_Activity.class));
            }
        });
    }
}