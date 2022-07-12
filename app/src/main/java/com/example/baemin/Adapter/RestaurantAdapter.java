package com.example.baemin.Adapter;

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
import com.example.baemin.Model.RestaurantModel;
import com.example.baemin.R;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    ArrayList<RestaurantModel> resModel;

    public RestaurantAdapter(ArrayList<RestaurantModel> resModel) {
        this.resModel = resModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurent, parent, false);

        return new ViewHolder(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String picUrl = resModel.get(position).getPic();
        holder.res_name.setText(resModel.get(position).getName());
        //holder.res_layout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.button_pink));
        int drawNumber = holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawNumber)
                .into(holder.res_pic);
    }

    @Override
    public int getItemCount() {
        return resModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView res_name;
        ImageView res_pic;
        ConstraintLayout res_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            res_name = itemView.findViewById(R.id.res_name);
            res_pic = itemView.findViewById(R.id.res_pic);
            res_layout = itemView.findViewById(R.id.res_layout);
        }
    }
}
