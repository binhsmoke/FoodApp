package com.example.baemin.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.baemin.Interfaces.IFood;
import com.example.baemin.Model.Category;
import com.example.baemin.Model.Food;
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

public class FoodDao implements IFood {
    private String token;

    public FoodDao(String token) {
        this.token = token;
    }

    ServiceAPI requestInterface = new Retrofit.Builder()
            .baseUrl(ServiceAPI.BASE_SERVICE)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ServiceAPI.class);
    @Override
    public void GetFood(int id, Context context) {
        requestInterface.GetFood(token,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Food>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( ArrayList<Food> alFood) {
                        String jsFood =  new Gson().toJson(alFood);
                        SharedPreferences sharedPref;
                        SharedPreferences.Editor editor;
                        sharedPref = context.getSharedPreferences("Food", Context.MODE_PRIVATE);
                        editor = sharedPref.edit();
                        editor.putString("KEY_FOOD", jsFood);
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
