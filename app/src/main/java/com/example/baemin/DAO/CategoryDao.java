package com.example.baemin.DAO;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.baemin.Activity.MainActivity;
import com.example.baemin.Interfaces.ICategory;
import com.example.baemin.Model.Category;
import com.example.baemin.Model.Token;
import com.example.baemin.Services.ServiceAPI;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryDao implements ICategory {
    private  String token;

    public CategoryDao(String token) {
        this.token = token;
    }

    ServiceAPI requestInterface = new Retrofit.Builder()
            .baseUrl(ServiceAPI.BASE_SERVICE)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ServiceAPI.class);
    @Override
    public void GetCategory(Context context) {
        requestInterface.GetCategory(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Category>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( ArrayList<Category> alCategory) {
                        String jsCategory =  new Gson().toJson(alCategory);
                        SharedPreferences sharedPref;
                        SharedPreferences.Editor editor;
                        sharedPref = context.getSharedPreferences("Category", Context.MODE_PRIVATE);
                        editor = sharedPref.edit();
                        editor.putString("KEY_CATEGORY", jsCategory);
                        editor.commit();
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
}
