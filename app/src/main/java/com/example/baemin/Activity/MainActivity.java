package com.example.baemin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.baemin.Helpers.MasjoheunSQLite;
import com.example.baemin.Listener.RecyclerItemClickListener;
import com.example.baemin.Model.Banner;
import com.example.baemin.Model.Cart;
import com.example.baemin.Model.Category;
import com.example.baemin.Model.Constants;
import com.example.baemin.R;
import com.example.baemin.Services.FetchAddressIntent;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter res_adapter, food_adapter;
    private RecyclerView rvResList, rvFoodList;
    String jsCategory;
    Runnable mRunnable;
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
    TextView tvLocation;
    AddressResultReceiver addressResultReceiver;
    LinearLayout llLocation;
    RecyclerView rvBanner;
    Timer timer;
    TimerTask timerTask;
    int bannerPosition;
    LinearLayoutManager LBanner;
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
                //. Banner
        rvBanner = findViewById(R.id.rvBanner);
        initBanner();
                //  . End Banner
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
                Toast.makeText(this, "Kh??ng th??? l???y v??? tr?? hi???n t???i", Toast.LENGTH_SHORT).show();
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

            // resultCode ???????c set b???i DetailActivity
            // RESULT_OK ch??? ra r???ng k???t qu??? n??y ???? th??nh c??ng
            if (resultCode == Activity.RESULT_OK) {
                // Nh???n d??? li???u t??? Intent tr??? v???
                final String location = data.getStringExtra(AddressForResultActivity.EXTRA_LOCATION_DATA);
                // S??? d???ng k???t qu??? result
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
                // DetailActivity kh??ng th??nh c??ng, kh??ng c?? data tr??? v???.
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

    private void initBanner() {
        ArrayList<Banner> alBanner = new ArrayList<>();
        alBanner.add(new Banner("banner1", R.drawable.app_banner__giaroi));
        alBanner.add(new Banner("banner2", R.drawable.app_banner_2));
        alBanner.add(new Banner("banner3", R.drawable.app_banner_3));
        alBanner.add(new Banner("banner4", R.drawable.app_banner_4));
        alBanner.add(new Banner("banner5", R.drawable.app_banner_5));
        alBanner.add(new Banner("banner6", R.drawable.app_banner_6));
        BannerAdapter ABanner = new BannerAdapter(this,alBanner);
        LBanner = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvBanner.setLayoutManager(LBanner);
        rvBanner.setAdapter(ABanner);
        bannerPosition = Integer.MAX_VALUE/2 ;
        rvBanner.scrollToPosition(bannerPosition);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvBanner);
        rvBanner.smoothScrollBy(5,0);
        rvBanner.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) {
                    bannerPosition=LBanner.findFirstCompletelyVisibleItemPosition();
                    runBanner();
                } else if (newState == 1) {
                    stopBanner();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        runBanner();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopBanner();
    }

    private void runBanner() {
        if (timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (bannerPosition == Integer.MAX_VALUE) {
                        bannerPosition = Integer.MAX_VALUE / 2;
                        rvBanner.scrollToPosition(bannerPosition);
                        rvBanner.smoothScrollBy(5, 0);
                    } else {
                        bannerPosition++;
                        rvBanner.smoothScrollToPosition(bannerPosition);
                    }
                }
            };
            timer.schedule(timerTask,3000,3000);
        }
    }

    private void stopBanner() {
        if (timer != null && timerTask != null) {
            timer.cancel();
            timerTask.cancel();
            timer = null;
            timerTask = null;
            bannerPosition= LBanner.findFirstCompletelyVisibleItemPosition();
        }
    }
}
