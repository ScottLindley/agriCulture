package com.scottlindley.farmgroceryapp.CartActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.scottlindley.farmgroceryapp.CustomObjects.Cart;
import com.scottlindley.farmgroceryapp.CustomObjects.Food;
import com.scottlindley.farmgroceryapp.CustomObjects.Order;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.FarmListActivity.FarmListActivity;
import com.scottlindley.farmgroceryapp.LikedFarmsActivity.LikedFarmsActivity;
import com.scottlindley.farmgroceryapp.OrderHistoryActivity.OrderHistoryActivity;
import com.scottlindley.farmgroceryapp.R;
import com.scottlindley.farmgroceryapp.SettingsActivity.SettingsActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.scottlindley.farmgroceryapp.R.id.toolbar;

public class CartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CartRecyclerAdapter.QuantityButtonListener {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private CartRecyclerAdapter mAdapter;
    private TextView mSubTotal, mTotal, mTax;
    private CardView mPlaceOrderButton;
    private double mRoundedSubTotal, mRoundedTax, mRoundedTotal;
    private int mUserID;

    @Override
    public void handleIncrement() {
        roundNumbers();

        setRoundedPrices();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mToolbar = (Toolbar) findViewById(toolbar);
        setSupportActionBar(mToolbar);

        SharedPreferences preferences = getSharedPreferences(FarmListActivity.PREFERENCES_KEY, MODE_PRIVATE);
        mUserID = preferences.getInt(FarmListActivity.DEVICE_USER_ID_KEY, -1);
        if(mUserID ==-1){finish();}

        setUpNavBar();

        setUpRecyclerView();

        setUpOrderButton();

        mSubTotal = (TextView)findViewById(R.id.subtotal);
        mTax = (TextView)findViewById(R.id.tax);
        mTotal = (TextView)findViewById(R.id.total);

        roundNumbers();

        setRoundedPrices();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_like) {
            startActivity(new Intent(this, LikedFarmsActivity.class));
        } else if (id == R.id.nav_order_history) {
            startActivity(new Intent(this, OrderHistoryActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_farm_list){
            startActivity(new Intent(this, FarmListActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setUpNavBar(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences preferences = getSharedPreferences(FarmListActivity.PREFERENCES_KEY, MODE_PRIVATE);
        int DeviceUserID = preferences.getInt(FarmListActivity.DEVICE_USER_ID_KEY, -1);

        View headerView = navigationView.getHeaderView(0);
        TextView navUserName = (TextView)headerView.findViewById(R.id.nav_user_name);
        TextView navUserState = (TextView)headerView.findViewById(R.id.nav_user_state);

        if(DeviceUserID!=-1) {
            navUserName.setText(
                    MySQLiteHelper.getInstance(CartActivity.this).getUserByID(DeviceUserID).getName());
            navUserState.setText(
                    MySQLiteHelper.getInstance(CartActivity.this).getUserByID(DeviceUserID).getState());
        }
    }

    public void setUpRecyclerView(){
        mRecyclerView = (RecyclerView)findViewById(R.id.cart_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        List<Food> cartItems = MySQLiteHelper.getInstance(CartActivity.this).getCartItemsByUserID(mUserID);
        mAdapter = new CartRecyclerAdapter(CartActivity.this, cartItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setUpOrderButton(){
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        final String todaysDate = dateFormat.format(date);

        mPlaceOrderButton = (CardView)findViewById(R.id.place_order_button);
        mPlaceOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySQLiteHelper.getInstance(CartActivity.this).deleteCartItemsByUserID(mUserID);
                Cart.getInstance().clearCart();
                mAdapter.replaceData();
                MySQLiteHelper.getInstance(CartActivity.this)
                        .insertOrder(new Order(mRoundedTotal, todaysDate, mUserID));
                Toast.makeText(CartActivity.this, "Order Confirmed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void roundNumbers(){
        mRoundedSubTotal = (Cart.getInstance().getSubTotal()*100);
        mRoundedSubTotal = Math.round(mRoundedSubTotal);
        mRoundedSubTotal = mRoundedSubTotal/100;
        mRoundedTax = Math.round(Cart.getInstance().getTax()*100);
        mRoundedTax = Math.round(mRoundedTax);
        mRoundedTax = mRoundedTax/100;
        mRoundedTotal = Math.round(Cart.getInstance().getTotal()*100);
        mRoundedTotal = mRoundedTotal/100;
    }

    public void setRoundedPrices(){
        mSubTotal.setText("$"+ mRoundedSubTotal);
        mTax.setText("$"+ mRoundedTax);
        mTotal.setText("$"+ mRoundedTotal);
    }

    @Override
    protected void onResume() {
        Cart.getInstance().removeZeroQuantities();
        mAdapter.replaceData();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Cart.getInstance().removeZeroQuantities();
        List<Food> cartItems = Cart.getInstance().getItems();
        MySQLiteHelper.getInstance(CartActivity.this).deleteCartItemsByUserID(mUserID);
        for(Food food : cartItems){
            MySQLiteHelper.getInstance(CartActivity.this).insertCartItem(food, mUserID);
        }
        super.onStop();
    }
}
