package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.baemin.DAO.ClientDao;
import com.example.baemin.R;

public class IfAccountExistActivity extends AppCompatActivity {
    TextView tvPhone, tvShowPassword;
    EditText edtPassword;
    LinearLayout buttonLogin;
    String phone, password;
    ProgressBar pbLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_if_account_exist);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tvPhone = findViewById(R.id.tvPhone);
        edtPassword = findViewById(R.id.edtPassword);
        buttonLogin = findViewById(R.id.llButtonLogin);
        pbLoading = findViewById(R.id.pbLoading);
        phone = bundle.getString("KEY_PHONE", null);
        tvShowPassword = findViewById(R.id.tvShowPassword);
        tvPhone.setText("Số diện thoại bạn đang đăng nhập là " + phone);
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = edtPassword.getText() + "";
                if (!password.isEmpty()) {
                    if (password.length() >= 6) {
                        buttonLogin.setVisibility(View.VISIBLE);
                    }
                    tvShowPassword.setVisibility(View.VISIBLE);
                    tvShowPassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (tvShowPassword.getText().toString().equals("Hiện")) {
                                edtPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                                edtPassword.setSelection(edtPassword.getText().length());
                                tvShowPassword.setText("Ẩn");
                            } else if (tvShowPassword.getText().toString().equals("Ẩn")) {
                                edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                                edtPassword.setSelection(edtPassword.getText().length());
                                tvShowPassword.setText("Hiện");
                            }
                        }
                    });
                }
                if (password.length() < 6) {
                    buttonLogin.setVisibility(View.GONE);
                }
                if (password.isEmpty()) {
                    buttonLogin.setVisibility(View.GONE);
                    tvShowPassword.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientDao clientDao = new ClientDao();
                clientDao.Login(phone, password, IfAccountExistActivity.this);
                pbLoading.setVisibility(View.VISIBLE);
                onRestart();
            }
        });
    }
    @Override
    public void onRestart() {
        super.onRestart();
        pbLoading.setVisibility(View.GONE);
    }
}