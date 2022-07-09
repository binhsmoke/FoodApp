package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baemin.R;

public class CreateAccountActivity extends AppCompatActivity {
    EditText edtName, edtPassword;
    ImageView ButtonCancelName;
    TextView tvShowPassword;
    LinearLayout buttonBegin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        ButtonCancelName = findViewById(R.id.imgButtonCancelName);
        tvShowPassword = findViewById(R.id.tvShowPassword);
        buttonBegin = findViewById(R.id.llButtonBegin);
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = edtName.getText() + "";
                if (!name.isEmpty()) {
                    String password = edtPassword.getText()+"";
                    if(!password.isEmpty() && password.length()>=6){
                        buttonBegin.setVisibility(View.VISIBLE);
                    }
                    ButtonCancelName.setVisibility(View.VISIBLE);
                    ButtonCancelName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edtName.setText("");
                            ButtonCancelName.setVisibility(View.GONE);
                            buttonBegin.setVisibility(View.GONE);
                        }
                    });
                }
                if (name.isEmpty()) {
                    buttonBegin.setVisibility(View.GONE);
                    ButtonCancelName.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = edtPassword.getText() + "";
                if (!password.isEmpty()) {
                    String name = edtName.getText()+"";
                    if(!name.isEmpty() && password.length()>=6){
                       buttonBegin.setVisibility(View.VISIBLE);
                    }
                    tvShowPassword.setVisibility(View.VISIBLE);
                    tvShowPassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(tvShowPassword.getText().toString().equals("Hiện")){
                                edtPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                                edtPassword.setSelection(edtPassword.getText().length());
                                tvShowPassword.setText("Ẩn");
                            }
                            else if(tvShowPassword.getText().toString().equals("Ẩn")){
                                edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                                edtPassword.setSelection(edtPassword.getText().length());
                                tvShowPassword.setText("Hiện");
                            }
                        }
                    });
                }
                if(password.length()<6){
                    buttonBegin.setVisibility(View.GONE);
                }
                if (password.isEmpty()) {
                    buttonBegin.setVisibility(View.GONE);
                    tvShowPassword.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}