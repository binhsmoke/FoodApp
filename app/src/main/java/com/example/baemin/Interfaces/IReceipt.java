package com.example.baemin.Interfaces;

import android.content.Context;

import com.example.baemin.Model.Receipt;
import com.example.baemin.Model.ReceiptAndDetail;
import com.example.baemin.Model.ReceiptDetail;

import java.util.List;

public interface IReceipt {
    String GenerateId(String phone);
    void Create(Context context, String token, ReceiptAndDetail receiptAndDetail);
}
