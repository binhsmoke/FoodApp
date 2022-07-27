package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baemin.Adapter.AddressAdapter;
import com.example.baemin.Listener.RecyclerItemClickListener;
import com.example.baemin.Model.PlaceApiModels.AddressResult;
import com.example.baemin.Model.PlaceApiModels.Prediction;
import com.example.baemin.Model.Token;
import com.example.baemin.R;
import com.example.baemin.Services.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressForResultActivity extends AppCompatActivity {
    EditText edtSearchAddress;
    RecyclerView rcvAddress;

    ServiceAPI serviceAPI;
    ArrayList<Prediction> alPredictions = new ArrayList<>();
    public static final String EXTRA_LOCATION_DATA = "EXTRA_LOCATION_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_for_result);


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .build();
        serviceAPI = retrofit.create(ServiceAPI.class);
        edtSearchAddress=findViewById(R.id.edtAddressSearch);
        rcvAddress = findViewById(R.id.rcvAddress);
        edtSearchAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getData(s.toString());
            }
        });
        rcvAddress.addOnItemTouchListener(new RecyclerItemClickListener(this, rcvAddress, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent data = new Intent();
                data.putExtra(EXTRA_LOCATION_DATA, alPredictions.get(position).getDescription());
                setResult(Activity.RESULT_OK, data);
                finish();
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private  void getData(String text){

        serviceAPI.getPlace(text,getString(R.string.hcmc_location_key),getString(R.string.components),getString(R.string.radius_key),getString(R.string.api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<AddressResult>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( AddressResult addressResult) {
                        alPredictions = addressResult.getPredictions();
                        AddressAdapter addressAdapter = new AddressAdapter(alPredictions,AddressForResultActivity.this);
                        LinearLayoutManager LLM = new LinearLayoutManager(AddressForResultActivity.this, LinearLayoutManager.VERTICAL, false);
                        rcvAddress.setLayoutManager(LLM);
                        rcvAddress.setAdapter(addressAdapter);
                    }
                    @Override

                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(AddressForResultActivity.this, "Error occured",Toast.LENGTH_SHORT).show();

                        }
                    @Override
                    public void onComplete() {}
                });
    }
}