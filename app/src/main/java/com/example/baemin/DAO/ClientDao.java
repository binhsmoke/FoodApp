package com.example.baemin.DAO;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.baemin.Activity.IfAccountExistActivity;
import com.example.baemin.Activity.IfAccountNotExistActivity;
import com.example.baemin.Activity.LoginActivity;
import com.example.baemin.Activity.MainActivity;
import com.example.baemin.Interfaces.IClient;
import com.example.baemin.Model.Client;
import com.example.baemin.Model.Message;
import com.example.baemin.Model.Token;
import com.example.baemin.Services.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientDao implements IClient {
    ServiceAPI requestInterface = new Retrofit.Builder()
            .baseUrl(ServiceAPI.BASE_SERVICE)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ServiceAPI.class);
    @Override
    public void CreateClient(Client client,Context context) {
        requestInterface.CreateClient(client)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( Token token) {
                        SharedPreferences sharedPref;
                        SharedPreferences.Editor editor;
                        sharedPref = context.getSharedPreferences("Client", Context.MODE_PRIVATE);
                        editor = sharedPref.edit();
                        editor.putString("KEY_PHONE", client.getPhone());
                        editor.putString("KEY_TOKEN", token.getToken());
                        editor.commit();

                        context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                    }
                    @Override

                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("AAA","LOI NE");
                        if(e instanceof java.net.UnknownHostException){
                            Toast.makeText(context,"Không có kết nối",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onComplete() {}
                });
    }

    @Override
    public void CheckClient(String phone, Context context) {
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
                        if(message.getMessage().equals("ok")) {
                            Intent intent = new Intent(context, IfAccountExistActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            Bundle bundle = new Bundle();
                            bundle.putString("KEY_PHONE", phone);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    }
                    @Override

                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("AAA","LOI NE");
                        if(e instanceof java.net.UnknownHostException){
                            Toast.makeText(context,"Không có kết nối",Toast.LENGTH_SHORT).show();
                        }else
                        if(((HttpException) e).code()==400){
                            Intent intent=new Intent(context, IfAccountNotExistActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            Bundle bundle = new Bundle();
                            bundle.putString("KEY_PHONE",phone);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    }
                    @Override
                    public void onComplete() {}
                });
    }

    @Override
    public void ChangePassword() {

    }

    @Override
    public void Login(String phone, String password, Context context) {
        requestInterface.LoginClient(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( Token token) {
                        SharedPreferences sharedPref;
                        SharedPreferences.Editor editor;
                        sharedPref = context.getSharedPreferences("Client", Context.MODE_PRIVATE);
                        editor = sharedPref.edit();
                        editor.putString("KEY_PHONE", phone);
                        editor.putString("KEY_TOKEN", token.getToken());
                        editor.commit();

                        context.startActivity(new Intent(context, MainActivity.class));

                    }
                    @Override

                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("AAA","LOI NE");
                        if(e instanceof java.net.UnknownHostException){
                            Toast.makeText(context,"Không có kết nối",Toast.LENGTH_SHORT).show();
                        }else
                        if(((HttpException) e).code()==400){
                            Toast.makeText(context,"Tài khoản hoặc mật khẩu không hợp lệ",Toast.LENGTH_SHORT).show();


                        }
                    }
                    @Override
                    public void onComplete() {}
                });
    }
}
