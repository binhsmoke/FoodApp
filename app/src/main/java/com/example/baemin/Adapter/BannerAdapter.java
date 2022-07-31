package com.example.baemin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baemin.DAO.CartDao;
import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Model.Banner;
import com.example.baemin.Model.Cart;
import com.example.baemin.R;

import java.util.ArrayList;


public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    private Context mContext;
    private ArrayList<Banner> alBanner;
    public BannerAdapter(Context context, ArrayList<Banner> alBanner) {
        this.mContext = context;
        this.alBanner = alBanner;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        return new BannerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Banner mBanner = alBanner.get(position%alBanner.size());
        Glide.with(mContext).load(mBanner.getBannerImg()).centerCrop().into(holder.imgBanner);
    }

    @Override
    public int getItemCount() {

        return Integer.MAX_VALUE;
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBanner;
        LinearLayout layout_cart;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_cart = itemView.findViewById(R.id.cart_item);
            imgBanner = itemView.findViewById(R.id.imgBanner);
        }
    }
}

