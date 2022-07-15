package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/*import com.example.baemin.Adapter.CartAdapter;*/
import com.example.baemin.Adapter.CartAdapter;
import com.example.baemin.DAO.CartDao;
import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Interfaces.IClick_Item;
import com.example.baemin.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvCart;
    CartAdapter adapterCart;
    TextView tvTotalCartQuantity, tvTotalPrice;
    Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rvCart = findViewById(R.id.cart_rv);
        tvTotalCartQuantity=findViewById(R.id.tvTotalCartQuantity);
        tvTotalPrice=findViewById(R.id.tvTotalCartPrice);
        CartDao cartDao = new CartDao();
        adapterCart = new CartAdapter(this,cartDao.Read(this,new MasjoheunSQLite(this)));
        LinearLayoutManager LLM = new LinearLayoutManager(this);
        rvCart.setLayoutManager(LLM);
        rvCart.setAdapter(adapterCart);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase sqLiteDatabase = new MasjoheunSQLite(CartActivity.this).getReadableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery("select count("+MasjoheunSQLite.KEY_QUANTITY+")"+" from "+MasjoheunSQLite.TABLE_RECEIPT_DETAIL,null);
                cursor.moveToFirst();
                int TotalQuantity = cursor.getInt(0);
                cursor = sqLiteDatabase.rawQuery("select sum("+MasjoheunSQLite.KEY_PRICE+")"+" from "+MasjoheunSQLite.TABLE_RECEIPT_DETAIL,null);
                cursor.moveToFirst();
                int TotalPrice = cursor.getInt(0);
                tvTotalCartQuantity.setText(TotalQuantity+" MÓN");
                tvTotalPrice.setText(TotalPrice+" VND");
                handler.postDelayed(this,100);
            }
        };
        this.runOnUiThread(runnable);
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    /*private IClick_Item iClick() {
        return new IClick_Item() {
            @Override
            public void onClickCartItem(FoodModel foodModel) {
                Intent i = new Intent(CartActivity.this, DetailActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("my_food", foodModel);
                i.putExtras(b);
                startActivity(i);
            }
        };
    }*/

}
