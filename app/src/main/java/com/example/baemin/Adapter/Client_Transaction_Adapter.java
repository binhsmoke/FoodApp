package com.example.baemin.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baemin.Activity.Client_Transaction_Activity;
import com.example.baemin.DAO.CartDao;
import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Model.Cart;
import com.example.baemin.Model.Receipt;
import com.example.baemin.R;
import com.example.baemin.Services.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Client_Transaction_Adapter extends RecyclerView.Adapter<Client_Transaction_Adapter.Client_TransactionViewHolder> {
    public Client_TransactionViewHolder client_transactionViewHolder;
    private Context mContext;
    private ArrayList<Receipt> alReceipt;
    private LayoutInflater layoutInflater;
    public Client_Transaction_Adapter(Context context, ArrayList<Receipt> alReceipt, LayoutInflater layoutInflater) {
        this.mContext = context;
        this.alReceipt = alReceipt;
        this.layoutInflater = layoutInflater;
    }


    @NonNull
    @Override
    public Client_TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client_transaction, parent, false);
        return new Client_TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Client_TransactionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvStatus.setText(alReceipt.get(position).getIsAccepted());
        holder.tvIdTransaction.setText(alReceipt.get(position).getId());
        holder.tvPrice.setText(alReceipt.get(position).getTotal()+"đ");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(alReceipt.get(position).getCreateDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);

        holder.tvDate.setText(formattedTime);
        holder.tvReOrder.setOnClickListener(v->{
            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
            View create=layoutInflater.inflate(R.layout.layout_alert_reorder_dialog,null);
            builder.setView(create);
            AlertDialog WarnDialog=builder.create();
            WarnDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            TextView okay=create.findViewById(R.id.btnOkay);
            TextView cancel=create.findViewById(R.id.btnCancel);
            WarnDialog.show();
            okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReOrder(alReceipt.get(position).getId());
                    WarnDialog.cancel();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WarnDialog.cancel();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        if (alReceipt != null) {
            return alReceipt.size();
        }
        return 0;
    }

    public class Client_TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus;
        TextView tvDate;
        TextView tvIdTransaction;
        TextView tvPrice;
        TextView tvReOrder;
        public Client_TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.client_transaction_status);
            tvDate = itemView.findViewById(R.id.client_transaction_time);
            tvIdTransaction = itemView.findViewById(R.id.client_transaction_id);
            tvPrice = itemView.findViewById(R.id.client_transaction_price);
            tvReOrder = itemView.findViewById(R.id.tvReOrder);
        }
    }
    void ReOrder(String id){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("Client",MODE_PRIVATE);
        String token = sharedPreferences.getString("KEY_TOKEN",null);
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(ServiceAPI.BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        requestInterface.GetReceiptDetail(token,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ArrayList<Cart>>() {
                    @Override
                    public void onSubscribe( Disposable d) {

                    }
                    @Override
                    public void onNext( ArrayList<Cart> alCart) {
                        CartDao cartDao = new CartDao();
                        cartDao.ReOrder(mContext,new MasjoheunSQLite(mContext),alCart);
                    }
                    @Override

                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("AAA","LOI NE");
                        if(e instanceof java.net.UnknownHostException){
                            Toast.makeText(mContext,"Không có kết nối",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onComplete() {}
                });
    }
}

