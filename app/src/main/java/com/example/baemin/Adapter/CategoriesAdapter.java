package com.example.baemin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baemin.Model.CategoriesModel;
import com.example.baemin.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    private Context mContext;
    private List<CategoriesModel> mList;

    public CategoriesAdapter(Context context) {
        this.mContext = context;
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
        holder.imgCat.setImageResource(catModel.getCat_pic());
        holder.tvCat.setText(catModel.getCat_name());
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
