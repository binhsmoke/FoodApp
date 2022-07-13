package com.example.baemin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; 
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baemin.Model.Food;
import com.example.baemin.R;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    List<Food> alFood;
    Context context;
    public FoodAdapter(List<Food> alFood, Context context) {
        this.alFood = alFood;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);

        return new ViewHolder(myview);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(alFood.get(position).getNameFood());
        holder.tvPrice.setText(alFood.get(position).getPriceFood()+" VND");
        Glide.with(context)
                .load(alFood.get(position).getImage())
                .into(holder.imgImage);
        holder.tvDescription.setText(alFood.get(position).getIngredients());
    }

    @Override
    public int getItemCount() {
        return alFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvDescription;
        ImageView imgImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvFoodName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            imgImage = itemView.findViewById(R.id.ivFoodImg);
        }
    }
}
