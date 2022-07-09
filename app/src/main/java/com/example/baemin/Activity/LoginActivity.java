package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baemin.Model.Message;
import com.example.baemin.R;
import com.example.baemin.Services.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText edtPhone;
    LinearLayout buttonNext;
    ImageView buttonCancel;
    ImageView buttonBack;
    TextView tvNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtPhone = findViewById(R.id.edtPhone);
        buttonNext = findViewById(R.id.llButtonNext);
        buttonCancel = findViewById(R.id.imgButtonCancel);
        buttonBack = findViewById(R.id.imgButtonBack);
        tvNext = findViewById(R.id.tvNext);
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
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.CheckClient(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( Message message) {
                        if(message.equals("ok")){
                            Toast.makeText(LoginActivity.this,"OK",Toast.LENGTH_SHORT);

                        }
                    }
                    @Override

                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("AAA","LOI NE");
                        if(e instanceof java.net.UnknownHostException){
                            Toast.makeText(LoginActivity.this,"Không có kết nối",Toast.LENGTH_SHORT).show();
                        }else
                            if(((HttpException) e).code()==400){
                                Intent intent=new Intent(LoginActivity.this,IfAccountNotExistActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("KEY_PHONE",phone);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                    }
                    @Override
                    public void onComplete() {}
                });
    }
}