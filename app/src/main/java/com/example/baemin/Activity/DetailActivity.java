package com.example.baemin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.baemin.DAO.CartDao;
import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Model.Cart;
import com.example.baemin.Model.Food;
import com.example.baemin.R;

public class DetailActivity extends AppCompatActivity {

    ImageView imgPic;
    TextView tvName, tvPrice, tvQuantity, totalPrice;
    Food food;
    int quantity=1, price;
    TextView tvFoodName, cart_less, cart_more;
    LinearLayout llButtonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imgPic = findViewById(R.id.detail_pic);
        tvName = findViewById(R.id.detail_name);
        tvPrice = findViewById(R.id.detail_price);
        tvQuantity = findViewById(R.id.detail_quantity);
        tvFoodName = findViewById(R.id.tvFoodName);

        llButtonAdd = findViewById(R.id.llBtnAdd);

        totalPrice = findViewById(R.id.detail_totalprice);
        cart_less = findViewById(R.id.cart_less);
        cart_more = findViewById(R.id.cart_more);

        Bundle bundle = getIntent().getExtras();
        food = (Food) bundle.getSerializable("KEY_FOOD");

        price = food.getPriceFood();


        Glide.with(this).load(food.getImage()).centerCrop().into(imgPic);
        tvName.setText(food.getNameFood());
        tvPrice.setText(food.getPriceFood()+" VND");
        totalPrice.setText(food.getPriceFood()+" VND");
        tvQuantity.setText(quantity+"");
        tvFoodName.setText(food.getNameFood());

        cart_less.setOnClickListener(v->setCart_less());
        cart_more.setOnClickListener(v->setCart_more());
        llButtonAdd.setOnClickListener(v-> setLlButtonAdd());
    }
    private void setCart_less(){
        if(quantity>1){
            quantity -= 1;
            price -= food.getPriceFood();
            tvQuantity.setText(quantity+"");
            totalPrice.setText(price+" VND");
        }
    }
    private void setCart_more(){
        quantity += 1;
        price += food.getPriceFood();
        tvQuantity.setText(quantity+"");
        totalPrice.setText(price+" VND");
    }
    private void setLlButtonAdd(){
        MasjoheunSQLite masjoheunSQLite = new MasjoheunSQLite(this);
        CartDao cartDao = new CartDao();
        Cart cart = new Cart(food.getId(),quantity,price,food.getImage(),food.getNameFood());
        cartDao.Create(this,masjoheunSQLite,cart);
        Toast.makeText(this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
        this.finish();
    }
}