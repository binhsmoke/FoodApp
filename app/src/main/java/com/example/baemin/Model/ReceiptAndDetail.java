package com.example.baemin.Model;

import java.util.List;

public class ReceiptAndDetail {
    private Receipt receipt;
    private List<ReceiptDetail> detailList;

    public ReceiptAndDetail(Receipt receipt, List<ReceiptDetail> detailList) {
        this.receipt = receipt;
        this.detailList = detailList;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public List<ReceiptDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ReceiptDetail> detailList) {
        this.detailList = detailList;
    }
}
