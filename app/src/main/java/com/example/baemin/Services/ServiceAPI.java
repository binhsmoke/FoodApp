package com.example.baemin.Services;

import com.example.baemin.Model.Message;

import io.reactivex.Observable;

import okhttp3.internal.http2.Http2;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface ServiceAPI {
    String BASE_SERVICE="https://masjoheun.luanntps19425.tokyo/";

    @GET("api/check-client")
    Observable<Message> CheckClient(@Query("phone") String phone);
}
