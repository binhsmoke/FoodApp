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


public class Client_Transaction_Adapter extends RecyclerView.Adapter<Client_Transaction_Adapter.Client_TransactionViewHolder> {

    private Context mContext;
    private ArrayList<Cart> alCart;
    public Client_Transaction_Adapter(Context context, ArrayList<Cart> alCart) {
        this.mContext = context;
        this.alCart = alCart;
    }


    @NonNull
    @Override
    public Client_TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new Client_TransactionViewHolder(view);
    }

    CartDao cartDao = new CartDao();
    @Override
    public void onBindViewHolder(@NonNull Client_TransactionViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvName.setText(alCart.get(position).getName());
        holder.tvPrice.setText(alCart.get(position).getPrice()+" VND");
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

    public class Client_TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity;
        TextView tvTime, tvStatus;
        LinearLayout transaction_item;

        public Client_TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.client_transaction_name);
            tvPrice = itemView.findViewById(R.id.client_transaction_price);
            tvQuantity = itemView.findViewById(R.id.client_transaction_quantity);
            tvTime = itemView.findViewById(R.id.client_transaction_time);
            tvStatus = itemView.findViewById(R.id.client_transaction_status);
            transaction_item = itemView.findViewById(R.id.layout_client_transaction_item);
        }
    }
}

