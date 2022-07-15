package com.example.baemin.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MasjoheunSQLite extends SQLiteOpenHelper {
    SQLiteDatabase db=this.getReadableDatabase();
    public static final String DATABASE_NAME="Masjoheun";
    public static final int DATABASE_VERSION=1;

    public static final String TABLE_RECEIPT_DETAIL="RecepitDetail";
    public static final String KEY_FOOD_ID="ID_Food";
    public static final String KEY_IMAGE="Image";
    public static final String KEY_QUANTITY="Quantity";
    public static final String KEY_PRICE="Price";
    public static final String KEY_NAME="Name";
    Context context;

    public MasjoheunSQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "create table "+TABLE_RECEIPT_DETAIL+"("
                +KEY_FOOD_ID+" integer " +","
                +KEY_QUANTITY+" integer "+","
                +KEY_PRICE+" integer "+","
                +KEY_IMAGE+" text "+","
                +KEY_NAME+" text "+","
                +"primary key"+"("+KEY_FOOD_ID+")"+")";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_RECEIPT_DETAIL);
        onCreate(db);
    }

}
