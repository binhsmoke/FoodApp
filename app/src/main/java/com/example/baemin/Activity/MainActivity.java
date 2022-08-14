package com.example.baemin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.baemin.Adapter.BannerAdapter;
import com.example.baemin.Adapter.CategoriesAdapter;
import com.example.baemin.DAO.CartDao;
import com.example.baemin.DAO.CategoryDao;
import com.example.baemin.DAO.ClientDao;
import com.example.baemin.Helpers.AutoScrollRecyclerView;
import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Listener.RecyclerItemClickListener;
import com.example.baemin.Model.Banner;
import com.example.baemin.Model.Cart;
import com.example.baemin.Model.Category;
import com.example.baemin.Model.Constants;
import com.example.baemin.R;
import com.example.baemin.Services.FetchAddressIntent;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String jsCategory;
    Handler mHandler;
    RecyclerView rcvCategory;
    LinearLayout llLoading;
    ImageView gifLoading;
    String token, phone;
    FloatingActionButton fabCart;
    TextView tvQuantityCart;
    ConstraintLayout clCart;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private static final int REQUEST_CODE_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    TextView tvLocation, tvFoodSearch;
    AddressResultReceiver addressResultReceiver;
    ImageView  btnMenu;
    AutoScrollRecyclerView rcvBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*add_to_rvResList();
        add_to_rvFoodList();*/
        gifLoading = findViewById(R.id.gifLoading);
        Glide.with(this).load(R.drawable.loading).into(new DrawableImageViewTarget(gifLoading));
        llLoading = findViewById(R.id.llLoading);
        rcvCategory = findViewById(R.id.rcvCategory);
        rcvBanner = findViewById(R.id.rvBanner);

        tvFoodSearch = findViewById(R.id.tvFoodSearch);
        tvFoodSearch.setOnClickListener(v-> startActivity(new Intent(this,FoodForResultActivity.class)));

        ArrayList<Banner> alBanner = new ArrayList<>();
        alBanner.add(new Banner(null,R.drawable.app_banner_2));
        alBanner.add(new Banner(null,R.drawable.app_banner_3));
        alBanner.add(new Banner(null,R.drawable.app_banner_4));
        alBanner.add(new Banner(null,R.drawable.app_banner_5));
        alBanner.add(new Banner(null,R.drawable.app_banner_6));
        rcvBanner.setAdapter(new BannerAdapter(this,alBanner));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        rcvBanner.setLayoutManager(linearLayoutManager);
        /*rcvBanner.smoothScrollToPosition(rcvBanner.getAdapter().getItemCount());*/
        rcvBanner.startAutoScroll();
        rcvBanner.setLoopEnabled(true);

        btnMenu = findViewById(R.id.icMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Client_Info_ChangePWD_Activity.class));
            }
        });

        mHandler = new Handler();

        tvLocation = findViewById(R.id.tvLocation);
        addressResultReceiver = new AddressResultReceiver(new Handler());

        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("Client", Context.MODE_PRIVATE);
        token = sharedPref.getString("KEY_TOKEN", null);
        phone = sharedPref.getString("KEY_PHONE", null);

        CategoryDao categoryDao = new CategoryDao(token);
        categoryDao.GetCategory(MainActivity.this);

        fabCart = findViewById(R.id.fbtCart);
        tvQuantityCart = findViewById(R.id.tvCartQuantity);
        clCart = findViewById(R.id.clCart);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Cart> alCart = new CartDao().Read(MainActivity.this, new MasjoheunSQLite(MainActivity.this));
                if (alCart.size() != 0) {
                    clCart.setVisibility(View.VISIBLE);
                    tvQuantityCart.setText(alCart.size() + "");
                    fabCart.setOnClickListener(v -> setFabCart());
                } else if (alCart.size() == 0)
                    clCart.setVisibility(View.GONE);
                Handler handler = new Handler();
                handler.postDelayed(this, 500);
            }
        });

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref;
                sharedPref = getSharedPreferences("Category", Context.MODE_PRIVATE);
                jsCategory = sharedPref.getString("KEY_CATEGORY", null);
                if (jsCategory != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Category>>() {
                    }.getType();
                    List<Category> alCategory = gson.fromJson(jsCategory, type);
                    rcvCategory.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    CategoriesAdapter categoriesAdapter = new CategoriesAdapter(MainActivity.this);
                    categoriesAdapter.loadAdapter(alCategory);
                    rcvCategory.setAdapter(categoriesAdapter);
                    llLoading.setVisibility(View.GONE);
                    rcvCategory.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, rcvCategory, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            SharedPreferences sharedPref;
                            SharedPreferences.Editor editor;
                            sharedPref = getSharedPreferences("Category", Context.MODE_PRIVATE);
                            editor = sharedPref.edit();
                            editor.putInt("KEY_ID_CATEGORY", alCategory.get(position).getId());
                            editor.putString("KEY_NAME_CATEGORY", alCategory.get(position).getNameType());
                            editor.commit();
                            startActivity(new Intent(MainActivity.this, FoodActivity.class));
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
                    mHandler.removeCallbacks(this);
                } else {
                    mHandler.postDelayed(this, 500);
                }
            }
        });
        askPermission();
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, AddressForResultActivity.class);
                startActivityForResult(intent, REQUEST_CODE_LOCATION);
            }
        });
    }

    void askPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            getCurrentLocation();
        else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getCurrentLocation();
            else
                Toast.makeText(this, "Không thể lấy vị trí hiện tại", Toast.LENGTH_SHORT).show();
    }

    void getCurrentLocation(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient( MainActivity.this);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        }else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                fetchAddressFromLatLong(location);
                            }
                        }
                    });
        }
    }
    void setFabCart(){
        startActivity(new Intent(this, CartActivity.class));
    }

    private void fetchAddressFromLatLong(Location location){
        Intent intent = new Intent(this, FetchAddressIntent.class);
        intent.putExtra(Constants.RECEIVER, addressResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOCATION) {

            // resultCode được set bởi DetailActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if (resultCode == Activity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                final String location = data.getStringExtra(AddressForResultActivity.EXTRA_LOCATION_DATA);
                // Sử dụng kết quả result
                tvLocation.setText(location);
                ClientDao clientDao = new ClientDao();
                clientDao.UpdateAddress(token,phone,location,MainActivity.this);
                SharedPreferences sharedPref;
                SharedPreferences.Editor editor;
                sharedPref = getSharedPreferences("Client", Context.MODE_PRIVATE);
                editor = sharedPref.edit();
                editor.putString("KEY_ADDRESS", location);
                editor.commit();
            } else {
                // DetailActivity không thành công, không có data trả về.
            }
        }
    }

    private  class AddressResultReceiver extends ResultReceiver {

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if(resultCode == Constants.SUCESS_RESULT){
                tvLocation.setText(resultData.getString(Constants.RESULT_DATA_KEY));
                ClientDao clientDao = new ClientDao();
                clientDao.UpdateAddress(token,phone,resultData.getString(Constants.RESULT_DATA_KEY),MainActivity.this);
                SharedPreferences sharedPref;
                SharedPreferences.Editor editor;
                sharedPref = getSharedPreferences("Client", Context.MODE_PRIVATE);
                editor = sharedPref.edit();
                editor.putString("KEY_ADDRESS", resultData.getString(Constants.RESULT_DATA_KEY));
                editor.commit();
            }else
                Toast.makeText(MainActivity.this,"J z cha",Toast.LENGTH_SHORT).show();
        }
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View create=getLayoutInflater().inflate(R.layout.layout_alert_reorder_dialog,null);
        builder.setView(create);
        AlertDialog WarnDialog=builder.create();
        WarnDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv = create.findViewById(R.id.tvContentAlert);
        tv.setText("Hông order nữa hả?");
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TextView okay=create.findViewById(R.id.btnOkay);
        okay.setText("Hông");
        TextView cancel=create.findViewById(R.id.btnCancel);
        cancel.setText("Ở lại");
        WarnDialog.show();
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                WarnDialog.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WarnDialog.cancel();
            }
        });
    }
}
