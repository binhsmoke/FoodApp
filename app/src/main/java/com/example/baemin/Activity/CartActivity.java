package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.baemin.Adapter.CartAdapter;
import com.example.baemin.Interface.IClickCartItem;
import com.example.baemin.Model.CartModel;
import com.example.baemin.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvCart;
    CartAdapter adapterCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rvCart = findViewById(R.id.cart_rv);
        adapterCart = new CartAdapter();
        LinearLayoutManager LLM = new LinearLayoutManager(this);
        rvCart.setLayoutManager(LLM);


        adapterCart.loadAdapter(getListCart(), iClick());
        rvCart.setAdapter(adapterCart);
    }


    private List<CartModel> getListCart() {
        List<CartModel> rawCartModel = new ArrayList<>();
        rawCartModel.add(new CartModel("Bánh gạo số 1", 100, 2, R.drawable.ic_vietflag));
        rawCartModel.add(new CartModel("Buổi trưa số 2", 200, 3, R.drawable.ic_vietflag));
        rawCartModel.add(new CartModel("Ramen số 3", 300, 4, R.drawable.ic_vietflag));
        rawCartModel.add(new CartModel("Canh Hàn Quôc 4", 400, 5, R.drawable.ic_vietflag));
        rawCartModel.add(new CartModel("Gà rán không xương phô mai 5", 500, 6, R.drawable.ic_vietflag));
        rawCartModel.add(new CartModel("Bánh gạo số 1", 100, 2, R.drawable.ic_vietflag));
        rawCartModel.add(new CartModel("Buổi trưa số 2", 200, 3, R.drawable.ic_vietflag));
        rawCartModel.add(new CartModel("Ramen số 3", 300, 4, R.drawable.ic_vietflag));
        rawCartModel.add(new CartModel("Canh Hàn Quôc 4", 400, 5, R.drawable.ic_vietflag));
        rawCartModel.add(new CartModel("Gà rán không xương phô mai 5", 500, 6, R.drawable.ic_vietflag));

        return rawCartModel;
    }

    private IClickCartItem iClick() {
        return new IClickCartItem() {
            @Override
            public void onClickCartItemAll(CartModel cartModel) {
                Intent i = new Intent(CartActivity.this, DetailActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("my_cartModel", cartModel);
                i.putExtras(b);
                startActivity(i);
            }
        };
    }

}