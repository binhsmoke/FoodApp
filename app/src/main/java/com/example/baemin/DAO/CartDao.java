package com.example.baemin.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.baemin.Activity.CartActivity;
import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Interfaces.ICart;
import com.example.baemin.Model.Cart;
import com.example.baemin.Model.ReceiptDetail;

import java.util.ArrayList;

public class CartDao implements ICart {
    @Override
    public void Create(Context context, MasjoheunSQLite db, Cart cart) {
        SQLiteDatabase sqLiteDatabase=db.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put(MasjoheunSQLite.KEY_FOOD_ID,cart.getIdFood());
        values.put(MasjoheunSQLite.KEY_QUANTITY,cart.getQuantity());
        values.put(MasjoheunSQLite.KEY_NAME,cart.getName());
        values.put(MasjoheunSQLite.KEY_PRICE,cart.getPrice());

        sqLiteDatabase.insert(MasjoheunSQLite.TABLE_RECEIPT_DETAIL,null,values);

    }

    @Override
    public ArrayList<Cart> Read(Context context, MasjoheunSQLite db) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+MasjoheunSQLite.TABLE_RECEIPT_DETAIL,null);
        ArrayList<Cart> alCart = new ArrayList<>();
        if(cursor.getCount() !=0){
            cursor.moveToFirst();
            do{
                alCart.add(new Cart(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3)));
            }while(cursor.moveToNext());
        }
        return alCart;
    }

    @Override
    public void Update(Context context, MasjoheunSQLite db, Cart cart) {
         SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
         ContentValues contentValues = new ContentValues();
         contentValues.put(MasjoheunSQLite.KEY_QUANTITY,cart.getQuantity());
         contentValues.put(MasjoheunSQLite.KEY_PRICE,cart.getPrice());
        sqLiteDatabase.update(MasjoheunSQLite.TABLE_RECEIPT_DETAIL,contentValues
                ,MasjoheunSQLite.KEY_FOOD_ID+"=?"
                ,new String[]{cart.getIdFood()+""});
    }

    @Override
    public void Delete(Context context, MasjoheunSQLite db, Cart cart) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        sqLiteDatabase.delete(MasjoheunSQLite.TABLE_RECEIPT_DETAIL
                ,MasjoheunSQLite.KEY_FOOD_ID+"=?"
                ,new String[]{cart.getIdFood()+""});
    }

    @Override
    public void DeleteAll(Context context, MasjoheunSQLite db) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from "+MasjoheunSQLite.TABLE_RECEIPT_DETAIL);
    }

    @Override
    public void ReOrder(Context context, MasjoheunSQLite db, ArrayList<Cart> alCart) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        sqLiteDatabase.execSQL("delete from "+MasjoheunSQLite.TABLE_RECEIPT_DETAIL);
        alCart.forEach(v->{
            ContentValues values=new ContentValues();
            values.put(MasjoheunSQLite.KEY_FOOD_ID,v.getIdFood());
            values.put(MasjoheunSQLite.KEY_QUANTITY,v.getQuantity());
            values.put(MasjoheunSQLite.KEY_NAME,v.getName());
            values.put(MasjoheunSQLite.KEY_PRICE,v.getPrice());

            sqLiteDatabase.insert(MasjoheunSQLite.TABLE_RECEIPT_DETAIL,null,values);
        });
        context.startActivity(new Intent(context, CartActivity.class));
    }

}
