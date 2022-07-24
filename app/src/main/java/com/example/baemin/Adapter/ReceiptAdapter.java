package com.example.baemin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baemin.DAO.CartDao;
import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Model.Cart;
import com.example.baemin.R;

import java.util.ArrayList;


public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.CartViewHolder> {



/*private List<FoodModel> mList;
    private IClick_Item iClickItem;
    */


    private Context mContext;
    private ArrayList<Cart> alCart;
    public ReceiptAdapter(Context context, ArrayList<Cart> alCart) {
        this.mContext = context;
        this.alCart = alCart;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt, parent, false);
        return new CartViewHolder(view);
    }

    CartDao cartDao = new CartDao();
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvName.setText(alCart.get(position).getName());
        holder.tvPrice.setText(alCart.get(position).getPrice()+" VND");
        holder.tvQuantity.setText(alCart.get(position).getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        if (alCart != null) {
            return alCart.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity;
        LinearLayout layout_cart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.cart_name);
            tvPrice = itemView.findViewById(R.id.cart_price);
            tvQuantity = itemView.findViewById(R.id.cart_quantity);
            layout_cart = itemView.findViewById(R.id.cart_item);
        }
    }
}

