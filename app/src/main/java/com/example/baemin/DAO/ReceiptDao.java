package com.example.baemin.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.baemin.Interfaces.IReceipt;
import com.example.baemin.Model.Food;
import com.example.baemin.Model.Message;
import com.example.baemin.Model.Receipt;
import com.example.baemin.Model.ReceiptAndDetail;
import com.example.baemin.Model.ReceiptDetail;
import com.example.baemin.Services.ServiceAPI;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReceiptDao implements IReceipt {
    ServiceAPI requestInterface = new Retrofit.Builder()
            .baseUrl(ServiceAPI.BASE_SERVICE)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ServiceAPI.class);
    @Override
    public String GenerateId(String phone) {
        char c1,c2,c3,c4,c5;
        c1 = (char)(Integer.parseInt(phone.substring(0,2))+65);
        c2 = (char)(Integer.parseInt(phone.substring(2,4))+65);
        c3 = (char)(Integer.parseInt(phone.substring(4,6))+65);
        c4 = (char)(Integer.parseInt(phone.substring(6,8))+65);
        c5 = (char)(Integer.parseInt(phone.substring(8))+65);
        LocalDateTime d = LocalDateTime.now();
        String hour = d.toLocalTime().getHour()+"";
        String sec = d.toLocalTime().getSecond()+"";
        String min = d.toLocalTime().getMinute()+"";
        return c1+""+c2+""+c3+""+c4+""+c5+""+sec+min+hour+"";
    }

    @Override
    public void Create(Context context, String token, ReceiptAndDetail receiptAndDetail) {
        requestInterface.CreateReceipt(token,receiptAndDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( Message msg) {
                        Toast.makeText(context,"Đặt hàng thành công!",Toast.LENGTH_SHORT).show();
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
