package com.example.baemin.Services;

import com.example.baemin.Model.Client;
import com.example.baemin.Model.Message;
import com.example.baemin.Model.Token;

import io.reactivex.Observable;

import okhttp3.internal.http2.Http2;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAPI {
    String BASE_SERVICE="https://masjoheun.luanntps19425.tokyo/";

    @GET("api/check-client")
    Observable<Message> CheckClient(@Query("phone") String phone);
    @POST("api/create-client")
    Observable<Token> CreateClient(@Body Client client);
    @GET("api/login-client")
    Observable<Token> LoginClient(@Query("phone") String phone, @Query("password") String passsword);

}
