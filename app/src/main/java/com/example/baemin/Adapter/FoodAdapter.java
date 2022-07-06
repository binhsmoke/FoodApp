package com.example.baemin.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baemin.Model.FoodModel;
import com.example.baemin.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    ArrayList<FoodModel> foodModel;

    public FoodAdapter(ArrayList<FoodModel> foodModel) {
        this.foodModel = foodModel;
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
        String picUrl = foodModel.get(position).getPicFood();
        holder.fname.setText(foodModel.get(position).getNameFood());
        holder.fprice.setText(Double.toString(foodModel.get(position).getPriceFood()));
        int drawNumber = holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawNumber)
                .into(holder.fpic);
    }

    @Override
    public int getItemCount() {
        return foodModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fname, fprice, fadd;
        ImageView fpic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.food_name);
            fprice = itemView.findViewById(R.id.food_price);
            fadd = itemView.findViewById(R.id.food_add);
            fpic = itemView.findViewById(R.id.food_pic);
        }
    }
}
