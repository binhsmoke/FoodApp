package com.example.baemin.Interfaces;

import android.content.Context;

import com.example.baemin.Model.Client;

public interface IClient {
    void CreateClient(Client client, Context context);
    void CheckClient(String phone, Context context);
    void ChangePassword();
    void Login(String phone, String password, Context context);
    void UpdateAddress(String token, String phone, String address, Context context);
}
