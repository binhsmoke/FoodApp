package com.example.baemin.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Interfaces.IReceiptDetailt;
import com.example.baemin.Model.Cart;
import com.example.baemin.Model.ReceiptDetail;

import java.util.ArrayList;
import java.util.List;

public class ReceiptDetailDao implements IReceiptDetailt {

    @Override
    public List<ReceiptDetail> Read(Context context, MasjoheunSQLite db) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+MasjoheunSQLite.TABLE_RECEIPT_DETAIL,null);
        List<ReceiptDetail> alReceipt = new ArrayList<>();
        if(cursor.getCount() !=0){
            cursor.moveToFirst();
            do{
                alReceipt.add(new ReceiptDetail(cursor.getInt(0),
                        null,
                        cursor.getInt(1),
                        cursor.getInt(2)
                        ));
            }while(cursor.moveToNext());
        }
        return alReceipt;
    }
}
