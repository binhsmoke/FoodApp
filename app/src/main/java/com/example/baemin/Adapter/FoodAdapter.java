package com.example.baemin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baemin.Interfaces.IClick_Item;
import com.example.baemin.Model.FoodModel;
import com.example.baemin.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<FoodModel> mList;
    private IClick_Item iClickItem;

    public void loadAdapter(List<FoodModel> list,IClick_Item iClickItem) {
        this.mList = list;
        this.iClickItem = iClickItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodModel foodModel = mList.get(position);
        if (foodModel == null) {
            return;
        }
        holder.tvName.setText(foodModel.getFood_name());
        holder.tvPrice.setText(String.valueOf(foodModel.getFood_price()));
        holder.imgPic.setImageResource(foodModel.getFood_pic());
        holder.layout_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItem.onClickCartItem(foodModel);
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

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgPic;
        LinearLayout layout_food;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.food_name);
            tvPrice = itemView.findViewById(R.id.food_price);
            imgPic = itemView.findViewById(R.id.food_pic);
            layout_food = itemView.findViewById(R.id.food_item);
        }
    }


}
