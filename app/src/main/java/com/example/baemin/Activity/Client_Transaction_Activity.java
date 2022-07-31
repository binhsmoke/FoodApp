package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.baemin.Adapter.Client_Transaction_Adapter;
import com.example.baemin.DAO.CartDao;
import com.example.baemin.DAO.ReceiptDao;
import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Listener.RecyclerItemClickListener;
import com.example.baemin.Model.Cart;
import com.example.baemin.Model.Food;
import com.example.baemin.Model.Receipt;
import com.example.baemin.Model.ReceiptDetail;
import com.example.baemin.R;
import com.example.baemin.Services.ServiceAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client_Transaction_Activity extends AppCompatActivity {
    private RecyclerView rcvTransaction;
    private Client_Transaction_Adapter client_transaction_adapter;
    ArrayList<Receipt> alReceipt;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_transaction);
        sharedPreferences = getSharedPreferences("Client", MODE_PRIVATE);
        String token = sharedPreferences.getString("KEY_TOKEN", null);
        String phone = sharedPreferences.getString("KEY_PHONE", null);
        ReceiptDao receiptDao = new ReceiptDao();
        receiptDao.Read(this, token, phone);
        rcvTransaction = findViewById(R.id.cart_rv);
        Handler handler = new Handler();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences("Receipt", MODE_PRIVATE);
                String jsAlReceipt = sharedPreferences.getString("KEY_RECEIPT", null);
                if (jsAlReceipt != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Receipt>>() {
                    }.getType();
                    alReceipt = gson.fromJson(jsAlReceipt, type);
                    client_transaction_adapter = new Client_Transaction_Adapter(Client_Transaction_Activity.this, alReceipt,getLayoutInflater());
                    LinearLayoutManager LLM = new LinearLayoutManager(Client_Transaction_Activity.this);
                    rcvTransaction.setLayoutManager(LLM);
                    rcvTransaction.setAdapter(client_transaction_adapter);
                    handler.removeCallbacks(this);
                } else
                    handler.postDelayed(this, 200);
            }
        });
        /*Handler handler1 = new Handler();
        Client_Transaction_Activity.this.runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                SharedPreferences sharedPreferences=getSharedPreferences("NEW_ORDER",MODE_PRIVATE);
                String receipt=sharedPreferences.getString("KEY_ORDER",null);
                if(receipt!=null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<Receipt>() {
                    }.getType();
                    Receipt receipt1 = gson.fromJson(receipt, type);
                    alReceipt.add(0, receipt1);
                    client_transaction_adapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences1 = getSharedPreferences("NEW_ORDER", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString("KEY_ORDER", null);
                    editor.apply();
                }
                handler1.postDelayed(this, 200);
            }
          });*/
    }
    @SuppressLint("NotifyDataSetChanged")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Receipt receipt) {
        alReceipt.add(0, receipt);
        client_transaction_adapter.notifyDataSetChanged();
        Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
        // do something
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
