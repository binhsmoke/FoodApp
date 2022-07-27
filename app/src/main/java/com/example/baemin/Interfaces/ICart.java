package com.example.baemin.Interfaces;

import android.content.Context;

import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Model.Cart;

import java.util.ArrayList;

public interface ICart {
    void Create(Context context, MasjoheunSQLite db, Cart cart);
    ArrayList<Cart> Read(Context context, MasjoheunSQLite db);
    void Update(Context context, MasjoheunSQLite db, Cart cart);
    void Delete(Context context, MasjoheunSQLite db, Cart cart);
    void DeleteAll(Context context, MasjoheunSQLite db);
}
