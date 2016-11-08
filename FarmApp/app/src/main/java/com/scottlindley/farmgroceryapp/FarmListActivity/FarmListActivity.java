package com.scottlindley.farmgroceryapp.FarmListActivity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CartActivity.CartActivity;
import com.scottlindley.farmgroceryapp.CustomObjects.Cart;
import com.scottlindley.farmgroceryapp.CustomObjects.Farm;
import com.scottlindley.farmgroceryapp.CustomObjects.Food;
import com.scottlindley.farmgroceryapp.Database.DBAssetsHelper;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.LikedFarmsActivity.LikedFarmsActivity;
import com.scottlindley.farmgroceryapp.OrderHistoryActivity.OrderHistoryActivity;
import com.scottlindley.farmgroceryapp.R;
import com.scottlindley.farmgroceryapp.SettingsActivity.SettingsActivity;
import com.scottlindley.farmgroceryapp.SignUpActivity.SignUpActivity;

import java.util.List;

public class FarmListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String PREFERENCES_KEY = "preferences";
    public static final String DEVICE_USER_ID_KEY = "device_user_id";
    private int mDeviceUserID;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private FarmListRecyclerAdapter mAdapter;
    private FloatingActionButton mFAB;
    private List<Farm> mFarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DBAssetsHelper dbAssetSetUp = new DBAssetsHelper(FarmListActivity.this);
        dbAssetSetUp.getReadableDatabase();

        checkForDeviceUser();

        setUpRecyclerView();

        setUpFloatingActionButton();

        setUpNavBar();

        lookForExistingCart();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        ComponentName componentName = new ComponentName(this, FarmListActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<Farm> searchedFarms = MySQLiteHelper.getInstance(FarmListActivity.this)
                    .searchFarms(query.toLowerCase());
            mAdapter.replaceData(searchedFarms);
        }
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_like) {
            startActivity(new Intent(this, LikedFarmsActivity.class));
        } else if (id == R.id.nav_cart) {
            startActivity(new Intent(this, CartActivity.class));
        } else if (id == R.id.nav_order_history) {
            startActivity(new Intent(this, OrderHistoryActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setUpFloatingActionButton(){
        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarmListActivity.this, CartActivity.class));
            }
        });
    }

    public void checkForDeviceUser(){
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_KEY, MODE_PRIVATE);
        mDeviceUserID = preferences.getInt(DEVICE_USER_ID_KEY, -1);
        if (mDeviceUserID==-1) {
            startActivity(new Intent(FarmListActivity.this, SignUpActivity.class));
            finish();
        }
    }

    public void setUpRecyclerView(){
        mFarms = MySQLiteHelper.getInstance(FarmListActivity.this).getAllFarms();

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(FarmListActivity.this, 2));

        mAdapter = new FarmListRecyclerAdapter(mFarms);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setUpNavBar(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUserName = (TextView)headerView.findViewById(R.id.nav_user_name);
        TextView navUserState = (TextView)headerView.findViewById(R.id.nav_user_state);

        if(mDeviceUserID!=-1) {
            navUserName.setText(
                    MySQLiteHelper.getInstance(FarmListActivity.this).getUserByID(mDeviceUserID).getName());
            navUserState.setText(
                    MySQLiteHelper.getInstance(FarmListActivity.this).getUserByID(mDeviceUserID).getState());
        }
    }

    public void lookForExistingCart() {
        List<Food> cartItems = MySQLiteHelper.getInstance(FarmListActivity.this)
                .getCartItemsByUserID(mDeviceUserID);
        if(!cartItems.isEmpty()) {
            Cart cart = Cart.getInstance();
            for (Food food : cartItems) {
                cart.getItems().add(food);
            }
        }
    }

}
