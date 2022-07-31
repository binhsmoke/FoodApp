package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.baemin.Adapter.CartAdapter;
import com.example.baemin.Adapter.ReceiptAdapter;
import com.example.baemin.DAO.CartDao;
import com.example.baemin.DAO.ReceiptDao;
import com.example.baemin.DAO.ReceiptDetailDao;
import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Model.Cart;
import com.example.baemin.Model.Receipt;
import com.example.baemin.Model.ReceiptAndDetail;
import com.example.baemin.Model.ReceiptDetail;
import com.example.baemin.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    private String address;
    private TextView tvAddress, tvQuantity, tvTotal, tvPrice;
    private RecyclerView rcvReceipt;
    private ArrayList<Cart> alCart = new ArrayList<>();
    private int TotalQuantity;
    private int TotalPrice;
    private EditText edtNote;
    private TextView btnCharge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        //link dum a nhe ,
        //ok anh
        tvAddress= findViewById(R.id.tvAddress);
        rcvReceipt = findViewById(R.id.receipt_rv);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotal = findViewById(R.id.tvTotal);
        tvPrice = findViewById(R.id.tvPrice);
        edtNote=findViewById(R.id.edtNote);
        btnCharge = findViewById(R.id.btnCharge);
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("Client", Context.MODE_PRIVATE);
        address=sharedPref.getString("KEY_ADDRESS", null);
        tvAddress.setText(address);
        CartDao cartDao = new CartDao();
        alCart = cartDao.Read(this,new MasjoheunSQLite(this));
        ReceiptAdapter adapterReceipt = new ReceiptAdapter(this,alCart);
        LinearLayoutManager LLM = new LinearLayoutManager(this);
        rcvReceipt.setLayoutManager(LLM);
        rcvReceipt.setAdapter(adapterReceipt);

        SQLiteDatabase sqLiteDatabase = new MasjoheunSQLite(ReceiptActivity.this).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select sum("+MasjoheunSQLite.KEY_QUANTITY+")"+" from "+MasjoheunSQLite.TABLE_RECEIPT_DETAIL,null);
        cursor.moveToFirst();
        TotalQuantity = cursor.getInt(0);
        cursor = sqLiteDatabase.rawQuery("select sum("+MasjoheunSQLite.KEY_PRICE+")"+" from "+MasjoheunSQLite.TABLE_RECEIPT_DETAIL,null);
        cursor.moveToFirst();
        TotalPrice = cursor.getInt(0);
        tvQuantity.setText(TotalQuantity+" món");
        tvPrice.setText(TotalPrice+"đ");
        tvTotal.setText(TotalPrice+"đ");

        btnCharge.setOnClickListener(v->Charge());
    }
    private void Charge(){

        LocalDateTime d = LocalDateTime.now();
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("Client", Context.MODE_PRIVATE);
        String phone=sharedPref.getString("KEY_PHONE", null);
        String token=sharedPref.getString("K" +
                "EY_TOKEN", null);
        ReceiptDao receiptDao = new ReceiptDao();
        String id=receiptDao.GenerateId(phone);
        String note = edtNote.getText()+"";
        ReceiptDetailDao receiptDetailDao = new ReceiptDetailDao();
        List<ReceiptDetail> alDetails=receiptDetailDao.Read(this,new MasjoheunSQLite(this));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Receipt receipt = new Receipt(id,TotalPrice,dtf.format(now),note,"wait",1,phone,null);
        sharedPref = getSharedPreferences("NEW_ORDER",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("KEY_ORDER",new Gson().toJson(receipt));
        editor.apply();
        Log.i("LOG", new Gson().toJson(new ReceiptAndDetail(receipt,alDetails)));
        receiptDao.Create(this,token,new ReceiptAndDetail(receipt,alDetails));
        CartDao cartDao = new CartDao();
        cartDao.DeleteAll(this,new MasjoheunSQLite(this));
        finish();
    }
}