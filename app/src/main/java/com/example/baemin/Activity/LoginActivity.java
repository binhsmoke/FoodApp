package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.baemin.DAO.ClientDao;
import com.example.baemin.R;

public class LoginActivity extends AppCompatActivity {
    EditText edtPhone;
    LinearLayout buttonNext;
    ImageView buttonCancel;
    ImageView buttonBack;
    TextView tvNext;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtPhone = findViewById(R.id.edtPhone);
        buttonNext = findViewById(R.id.llButtonNext);
        buttonCancel = findViewById(R.id.imgButtonCancel);
        buttonBack = findViewById(R.id.imgButtonBack);
        tvNext = findViewById(R.id.tvNext);
        progressBar = findViewById(R.id.pbLoading);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone = edtPhone.getText() + "";
                if (!phone.isEmpty()) {
                    buttonCancel.setVisibility(View.VISIBLE);
                    buttonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edtPhone.setText("");
                            buttonNext.setVisibility(View.GONE);
                        }
                    });
                }
                if (phone.isEmpty()) {
                    buttonNext.setVisibility(View.GONE);
                    buttonCancel.setVisibility(View.GONE);
                }
                if(phone.length()==10){
                    buttonNext.setOnClickListener(v -> OnSubmit(edtPhone.getText()+"") );
                    buttonNext.setVisibility(View.VISIBLE);
                }
                if(phone.length()!=10){
                    buttonNext.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if(edtPhone.getText().toString().length()==10){

            buttonNext.setVisibility(View.VISIBLE);
            buttonNext.setOnClickListener(v -> OnSubmit(edtPhone.getText()+"") );
        }
    }

    private void OnSubmit(String phone){
        ClientDao cLientDao = new ClientDao();
        progressBar.setVisibility(View.VISIBLE);
        cLientDao.CheckClient(phone, LoginActivity.this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progressBar.setVisibility(View.GONE);
    }
}