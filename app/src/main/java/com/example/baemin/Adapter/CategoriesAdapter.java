package com.example.baemin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baemin.Activity.DetailActivity;
import com.example.baemin.Activity.FoodActivity;
import com.example.baemin.Interfaces.IClick_Item;
import com.example.baemin.Model.CategoriesModel;
import com.example.baemin.R;

import java.io.Serializable;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private List<CategoriesModel> mList;
    private Context mContext;

    public CategoriesAdapter( Context mContext) {
        this.mContext = mContext;
    }

    public void loadAdapter(List<CategoriesModel> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        CategoriesModel catModel = mList.get(position);
        if (catModel == null) {
            return;
        }
        holder.tvCat.setText(catModel.getCat_name());
        holder.imgCat.setImageResource(catModel.getCat_pic());
        holder.imgCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, FoodActivity.class);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCat;
        TextView tvCat;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCat = itemView.findViewById(R.id.cat_img);
            tvCat = itemView.findViewById(R.id.cat_tv);
        }
    }
}
