package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.baemin.R;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText edtPhone;
    LinearLayout buttonNext;
    ImageView buttonCancel;
    ImageView buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtPhone = findViewById(R.id.edtPhone);
        buttonNext = findViewById(R.id.llButtonNext);
        buttonCancel = findViewById(R.id.imgButtonCancel);
        buttonBack = findViewById(R.id.imgButtonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    OnPhoneChange();
                }
                return false;
            }
        };
        edtPhone.setOnKeyListener(keyListener);
    }
    private void OnPhoneChange() {
        String phone = edtPhone.getText() + "";
        if (!phone.isEmpty()) {
            buttonNext.setBackgroundResource(R.drawable.focus_button);
            buttonCancel.setVisibility(View.VISIBLE);
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtPhone.setText("");
                    buttonNext.setBackgroundResource(R.drawable.unfocus_button);
                }
            });
        }
        if (phone.isEmpty()) {
            buttonNext.setBackgroundResource(R.drawable.unfocus_button);
            buttonCancel.setVisibility(View.GONE);
        }
    }
}