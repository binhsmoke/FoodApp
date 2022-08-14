package com.example.baemin.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baemin.Adapter.AddressAdapter;
import com.example.baemin.Adapter.FoodAdapter;
import com.example.baemin.Listener.RecyclerItemClickListener;
import com.example.baemin.Model.Food;
import com.example.baemin.Model.PlaceApiModels.AddressResult;
import com.example.baemin.Model.PlaceApiModels.Prediction;
import com.example.baemin.R;
import com.example.baemin.Services.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodForResultActivity extends AppCompatActivity {
    EditText edtSearchFood;
    RecyclerView rcvFood;

    ServiceAPI serviceAPI;
    ArrayList<Food> alFood = new ArrayList<>();
    FoodAdapter foodAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_for_result);


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .build();
        serviceAPI = retrofit.create(ServiceAPI.class);
        edtSearchFood=findViewById(R.id.edtFoodSearch);
        rcvFood = findViewById(R.id.rcvFood);
        edtSearchFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getData(edtSearchFood.getText().toString()) ;
            }
        });
        rcvFood.addOnItemTouchListener(new RecyclerItemClickListener(this, rcvFood, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Food food = alFood.get(position);
                Intent intent = new Intent(FoodForResultActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("KEY_FOOD",food);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        getData("G");
    }

    private  void getData(String text){
        String token;
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("Client", Context.MODE_PRIVATE);
        token = sharedPref.getString("KEY_TOKEN",null);

        serviceAPI.FindFood(token,text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Food>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( ArrayList<Food> foods) {
                        alFood = foods;
                        foodAdapter = new FoodAdapter(alFood, FoodForResultActivity.this);
                        LinearLayoutManager LLM = new LinearLayoutManager(FoodForResultActivity.this, LinearLayoutManager.VERTICAL, false);
                        rcvFood.setLayoutManager(LLM);
                        rcvFood.setAdapter(foodAdapter);
                    }
                    @Override

                    public void onError(Throwable e) {
                        e.printStackTrace();

                        }
                    @Override
                    public void onComplete() {}
                });
    }
}