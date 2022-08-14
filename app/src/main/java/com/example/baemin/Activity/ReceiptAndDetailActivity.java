package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baemin.Adapter.BannerAdapter;
import com.example.baemin.Adapter.HistoryCartAdapter;
import com.example.baemin.Model.Message;
import com.example.baemin.Model.ReceiptAndDetail;
import com.example.baemin.Model.ReceiptAndDetailHistory;
import com.example.baemin.R;
import com.example.baemin.Services.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReceiptAndDetailActivity extends AppCompatActivity {

    ServiceAPI requestInterface = new Retrofit.Builder()
            .baseUrl(ServiceAPI.BASE_SERVICE)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ServiceAPI.class);
    TextView tvDate, idReceipt, tvTotal, tvName, tvPhone, tvAddress;
    RecyclerView rcvHistoryDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_and_detail);
        tvDate = findViewById(R.id.tvDate);
        idReceipt = findViewById(R.id.tvIdReceiptHistory);
        tvTotal = findViewById(R.id.tvTotalDetail);
        tvName = findViewById(R.id.tvNameClient);
        tvPhone = findViewById(R.id.tvPhoneDetail);
        tvAddress = findViewById(R.id.tvAddressDetail);
        rcvHistoryDetail = findViewById(R.id.rcvDetail);
        SharedPreferences sharedPreferences = getSharedPreferences("Client",MODE_PRIVATE);
        String token = sharedPreferences.getString("KEY_TOKEN",null);
        String id = getIntent().getStringExtra("KEY_ID");
        requestInterface.GetReceiptAndDetail(token,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ReceiptAndDetailHistory>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( ReceiptAndDetailHistory receiptAndDetailHistory) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        SimpleDateFormat output = new SimpleDateFormat("'ngày 'dd',' 'tháng' MM', năm 'yyyy");
                        Date d = null;
                        try {
                            d = sdf.parse(receiptAndDetailHistory.getReceipt().getCreateDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedTime = output.format(d);
                        tvTotal.setText(receiptAndDetailHistory.getReceipt().getTotal()+"đ");
                        idReceipt.setText(receiptAndDetailHistory.getReceipt().getId());
                        tvDate.setText(formattedTime);
                        tvAddress.setText(receiptAndDetailHistory.getReceipt().getDeliveryAddress());
                        tvPhone.setText(receiptAndDetailHistory.getReceipt().getPhoneClient());
                        tvName.setText(sharedPreferences.getString("KEY_NAME",null));
                        rcvHistoryDetail.setAdapter(new HistoryCartAdapter(ReceiptAndDetailActivity.this,receiptAndDetailHistory.getCartList()));
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReceiptAndDetailActivity.this, LinearLayoutManager.VERTICAL,false);
                        rcvHistoryDetail.setLayoutManager(linearLayoutManager);
                    }
                    @Override

                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("AAA","LOI NE");
                        if(e instanceof java.net.UnknownHostException){
                            Toast.makeText(ReceiptAndDetailActivity.this,"Không có kết nối",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onComplete() {}
                });
    }
}