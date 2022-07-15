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
import com.example.baemin.Interfaces.IClick_Item;
import com.example.baemin.Model.Cart;
import com.example.baemin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {



/*private List<FoodModel> mList;
    private IClick_Item iClickItem;
    */


    private Context mContext;
    private ArrayList<Cart> alCart;
    public CartAdapter(Context context, ArrayList<Cart> alCart) {
        this.mContext = context;
        this.alCart = alCart;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    CartDao cartDao = new CartDao();
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvName.setText(alCart.get(position).getName());
        holder.tvPrice.setText(alCart.get(position).getPrice()+"");
        holder.tvQuantity.setText(alCart.get(position).getQuantity()+"");

        int quantity = alCart.get(position).getQuantity();
        int price = alCart.get(position).getPrice();
        holder.btnLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quantity==1){
                    cartDao.Delete(mContext, new MasjoheunSQLite(mContext), alCart.get(position));
                    alCart.remove(position);
                    notifyDataSetChanged();
                }else{
                    int currentQuantity = quantity-1;
                    int currentPrice = price - (price/quantity);
                    alCart.get(position).setQuantity(currentQuantity);
                    alCart.get(position).setPrice(currentPrice);
                    cartDao.Update(mContext, new MasjoheunSQLite(mContext), alCart.get(position));
                    notifyDataSetChanged();
                }
            }
        });
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = quantity+1;
                int currentPrice = price + (price/quantity);
                alCart.get(position).setQuantity(currentQuantity);
                alCart.get(position).setPrice(currentPrice);
                cartDao.Update(mContext, new MasjoheunSQLite(mContext), alCart.get(position));
                notifyDataSetChanged();
            }
        });
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

