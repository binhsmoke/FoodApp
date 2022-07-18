package com.example.baemin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*import com.example.baemin.Activity.FoodActivity;*/
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.baemin.Model.Category;
import com.example.baemin.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private List<Category> mList;
    private Context mContext;

    public CategoriesAdapter( Context mContext) {
        this.mContext = mContext;
    }

    public void loadAdapter(List<Category> list) {
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
        Category catModel = mList.get(position);
        if (catModel == null) {
            return;
        }
        holder.tvCat.setText(catModel.getNameType());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(15));
        Glide.with(mContext).load(catModel.getImage()).apply(requestOptions).into(holder.imgCat);
        /*holder.imgCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {*//*
                Intent i = new Intent(mContext, FoodActivity.class);
                mContext.startActivity(i);*//*
            }
        });*/

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
