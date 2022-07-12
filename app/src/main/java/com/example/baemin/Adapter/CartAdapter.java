package com.example.baemin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baemin.Interfaces.IClick_Item;
import com.example.baemin.Model.FoodModel;
import com.example.baemin.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<FoodModel> mList;
    private IClick_Item iClickItem;
    /*private Context mContext;

    public CartAdapter(Context context) {
        this.mContext = context;
    }*/

    public void loadAdapter(List<FoodModel> list, IClick_Item iClickItem) {
        this.mList = list;
        this.iClickItem = iClickItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        FoodModel foodModel = mList.get(position);
        if (foodModel == null) {
//            Toast.makeText(mContext, "Khong co gi trong gio hang", Toast.LENGTH_SHORT).show();
            return;
        }

        holder.tvName.setText(foodModel.getFood_name());
        holder.tvPrice.setText(String.valueOf(foodModel.getFood_price()));
        holder.tvQuantity.setText(String.valueOf(foodModel.getFood_quantity()));

        holder.btnLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluonggiam = foodModel.getFood_quantity()-1;
                if (soluonggiam<=0){
                    mList.remove(position);
                    notifyDataSetChanged();
                }else   {
                foodModel.setFood_quantity(soluonggiam);
                holder.tvQuantity.setText(String.valueOf(foodModel.getFood_quantity()));
                }
            }
        });
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongtang = foodModel.getFood_quantity()+1;
                foodModel.setFood_quantity(soluongtang);
                holder.tvQuantity.setText(String.valueOf(foodModel.getFood_quantity()));
            }
        });
        holder.layout_cart.setOnClickListener(new View.OnClickListener() {
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

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity;
        TextView btnMore, btnLess;
        LinearLayout layout_cart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.cart_name);
            tvPrice = itemView.findViewById(R.id.cart_price);
            tvQuantity = itemView.findViewById(R.id.cart_quantity);
            btnMore = itemView.findViewById(R.id.cart_more);
            btnLess = itemView.findViewById(R.id.cart_less);
            layout_cart = itemView.findViewById(R.id.cart_item);
        }
    }


}
