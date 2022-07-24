package com.example.baemin.Services;

import com.example.baemin.Model.Category;
import com.example.baemin.Model.Client;
import com.example.baemin.Model.Food;
import com.example.baemin.Model.PlaceApiModels.AddressResult;
import com.example.baemin.Model.Message;
import com.example.baemin.Model.Receipt;
import com.example.baemin.Model.ReceiptAndDetail;
import com.example.baemin.Model.ReceiptDetail;
import com.example.baemin.Model.Token;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAPI {
    String BASE_SERVICE="https://masjoheun.luanntps19425.tokyo/";

    @GET("place/autocomplete/json")
    Observable<AddressResult> getPlace(@Query("input") String text,@Query("location") String location,@Query("components") String components, @Query("radius") String radius, @Query("key") String key);
    @GET("api/check-client")
    Observable<Message> CheckClient(@Query("phone") String phone);
    @POST("api/create-client")
    Observable<Token> CreateClient(@Body Client client);
    @POST("api/update-address")
    Observable<Message> UpdateAddress(@Header("Authorization") String token,@Query("phone") String phone,@Query("address") String address);
    @GET("api/login-client")
    Observable<Token> LoginClient(@Query("phone") String phone, @Query("password") String passsword);

    @GET("api/get-food")
    Observable<ArrayList<Food>> GetFood(@Header("Authorization") String token,@Query("idType") int idType);
    @GET("api/get-category") 
    Observable<ArrayList<Category>> GetCategory(@Header("Authorization") String token);

    @POST("api/create-receipt")
    Observable<Message> CreateReceipt(@Header("Authorization") String token,@Body ReceiptAndDetail receiptAndDetail);
}
