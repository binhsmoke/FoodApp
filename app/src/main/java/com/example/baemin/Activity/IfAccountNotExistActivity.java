package com.example.baemin.Activity;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.interrupted;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baemin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class IfAccountNotExistActivity extends AppCompatActivity {
    private EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5,inputCode6;
    private TextView tvPhone, tvResend, tvCount;
    private LinearLayout buttonNextNotExist;
    static int wait=60;
    String code,phone,verificationID;
    Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_if_account_not_exist);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        phone = bundle.getString("KEY_PHONE",null);


        tvPhone=findViewById(R.id.tvPhone);
        tvPhone.setText(phone);
        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);
        tvCount = findViewById(R.id.tvCount);
        tvResend=findViewById(R.id.tvResend);

        handler = new Handler();
        if(wait==60){
            SendOTP(phone);
        }else{
            tvResend.setTextColor(ContextCompat.getColor(IfAccountNotExistActivity.this, R.color.silver));
            this.runOnUiThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    if (wait >0) {
                        tvResend.setText("Vui lòng đợi "+(wait-1)+" giây");
                        tvCount.setText(wait-1+"");
                        handler.postDelayed(this, 1000);
                    }
                    else if(wait<=0){
                        tvResend.setTextColor(ContextCompat.getColor(IfAccountNotExistActivity.this, R.color.cyan));
                        tvResend.setText("Gửi lại");
                        wait = 60;
                        handler.removeCallbacks(this);
                        tvResend.setOnClickListener(v -> SendOTP(phone));
                    }
                }
            }));
        }
        setupOTPInputs();


    }
    private void setupOTPInputs(){

        inputCode1.requestFocus();

        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                    checkInputOTP();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                    checkInputOTP();
                }if(s.toString().trim().isEmpty()){
                    inputCode1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                    checkInputOTP();
                }
                if(s.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                    checkInputOTP();
                }
                if(s.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){

                    inputCode6.requestFocus();

                }
                if(s.toString().trim().isEmpty()){
                    inputCode4.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }
                if(!s.toString().trim().isEmpty()){
                    checkInputOTP();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private void SendOTP(String phone){
        handler = new Handler();
        tvResend.setOnClickListener(null);
        tvResend.setTextColor(ContextCompat.getColor(IfAccountNotExistActivity.this, R.color.silver));
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvResend.setText("Vui lòng đợi "+wait+" giây");
                tvCount.setText(wait+"");
                if(wait>=1) {
                    wait=wait-1;
                    handler.postDelayed(this, 1000);
                }
                else if(wait==0){
                    tvResend.setTextColor(ContextCompat.getColor(IfAccountNotExistActivity.this, R.color.cyan));
                    tvResend.setText("Gửi lại");
                    tvResend.setOnClickListener(v->SendOTP(phone));
                    wait = 60;
                    handler.removeCallbacks(this);
                }
                System.out.println("asd");
            }
        });
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+84"+phone,
                60,
                TimeUnit.SECONDS,
                IfAccountNotExistActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(IfAccountNotExistActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        verificationID=s;
                    }
                });
    }
    private void CheckOTP(String code){
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                verificationID,
                code
        );
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent=new Intent(IfAccountNotExistActivity.this,CreateAccountActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("KEY_PHONE",phone);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else
                            Toast.makeText(IfAccountNotExistActivity.this,"ma sai",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void checkInputOTP(){
        if(!inputCode1.getText().toString().equals("")&&
                !inputCode2.getText().toString().equals("")&&
                !inputCode3.getText().toString().equals("")&&
                !inputCode4.getText().toString().equals("")&&
                !inputCode5.getText().toString().equals("")&&
                !inputCode6.getText().toString().equals("")) {
            code = inputCode1.getText().toString()
                    + inputCode2.getText().toString()
                    + inputCode3.getText().toString()
                    + inputCode4.getText().toString()
                    + inputCode5.getText().toString()
                    + inputCode6.getText().toString();
            CheckOTP(code);
        }
    }
}