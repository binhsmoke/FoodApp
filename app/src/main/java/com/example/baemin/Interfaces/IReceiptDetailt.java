package com.example.baemin.Interfaces;

import android.content.Context;

import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Model.Cart;
import com.example.baemin.Model.ReceiptDetail;

import java.util.ArrayList;
import java.util.List;

public interface IReceiptDetailt {
    List<ReceiptDetail> Read(Context context, MasjoheunSQLite db);
}
