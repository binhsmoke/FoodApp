package com.example.baemin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baemin.Interface.IClickCartItem;
import com.example.baemin.Model.CartModel;
import com.example.baemin.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartModel> mList;
    private IClickCartItem iClickCartItem;
    /*private Context mContext;

    public CartAdapter(Context context) {
        this.mContext = context;
    }*/

    public void loadAdapter(List<CartModel> list, IClickCartItem iClickCartItem) {
        this.mList = list;
        this.iClickCartItem=iClickCartItem;
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
        CartModel cartModel = mList.get(position);
        if (cartModel == null) {
//            Toast.makeText(mContext, "Khong co gi trong gio hang", Toast.LENGTH_SHORT).show();
            return;
        }

        holder.tvName.setText(cartModel.getCart_name());
        holder.tvPrice.setText(String.valueOf(cartModel.getCart_price()));
        holder.tvQuantity.setText(String.valueOf(cartModel.getCart_quantity()));

        holder.btnLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluonggiam = cartModel.getCart_quantity()-1;
                if (soluonggiam<=0){
                    mList.remove(position);
                    notifyDataSetChanged();
                }else   {
                cartModel.setCart_quantity(soluonggiam);
                holder.tvQuantity.setText(String.valueOf(cartModel.getCart_quantity()));
                }
            }
        });
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soluongtang = cartModel.getCart_quantity()+1;
                cartModel.setCart_quantity(soluongtang);
                holder.tvQuantity.setText(String.valueOf(cartModel.getCart_quantity()));
            }
        });
        holder.layout_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickCartItem.onClickCartItemAll(cartModel);
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
            layout_cart = itemView.findViewById(R.id.cart_layout);
        }
    }


}
