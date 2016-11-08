package com.scottlindley.farmgroceryapp.FarmActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CartActivity.CartActivity;
import com.scottlindley.farmgroceryapp.CustomObjects.Cart;
import com.scottlindley.farmgroceryapp.CustomObjects.Food;
import com.scottlindley.farmgroceryapp.CustomObjects.Like;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.FarmListActivity.FarmListActivity;
import com.scottlindley.farmgroceryapp.FarmListActivity.FarmListRecyclerAdapter;
import com.scottlindley.farmgroceryapp.LikedFarmsActivity.LikedFarmsActivity;
import com.scottlindley.farmgroceryapp.OrderHistoryActivity.OrderHistoryActivity;
import com.scottlindley.farmgroceryapp.R;
import com.scottlindley.farmgroceryapp.SettingsActivity.SettingsActivity;

import java.util.List;

import static com.scottlindley.farmgroceryapp.R.id.toolbar;

public class FarmActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int mSelectedFarmID;
    private Toolbar mToolbar;
    private MySQLiteHelper mHelper;
    private int mUserID;
    private OnLikeButtonListener mListener;
    private FarmPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);
        mToolbar = (Toolbar) findViewById(toolbar);
        setSupportActionBar(mToolbar);

        mHelper = MySQLiteHelper.getInstance(FarmActivity.this);

        SharedPreferences preferences = getSharedPreferences(FarmListActivity.PREFERENCES_KEY, MODE_PRIVATE);
        mUserID = preferences.getInt(FarmListActivity.DEVICE_USER_ID_KEY, -1);
        if(mUserID ==-1){finish();}

        getReceivedIntent();

        setUpFloatingActionButton();

        setUpNavBar();

        setUpViewPagerAndTabs();

        setToolBarTitle();
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
        } else if (id == R.id.nav_cart) {
            startActivity(new Intent(FarmActivity.this, CartActivity.class));
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

    public void getReceivedIntent(){
        Intent receivedIntent = getIntent();
        mSelectedFarmID = receivedIntent.getIntExtra(FarmListRecyclerAdapter.FARM_ID_INTENT_KEY, -1);
        if(mSelectedFarmID ==-1){
            finish();
        }
    }

    public void setUpFloatingActionButton(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarmActivity.this, CartActivity.class));
            }
        });
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
                    mHelper.getUserByID(DeviceUserID).getName());
            navUserState.setText(
                    mHelper.getUserByID(DeviceUserID).getState());
        }
    }

    public void setUpViewPagerAndTabs(){
        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        mPagerAdapter = new FarmPagerAdapter(getSupportFragmentManager(), mSelectedFarmID);
        viewPager.setAdapter(mPagerAdapter);

        mListener = (FarmLikesFragment) mPagerAdapter.getItem(2);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


    }

    public void setToolBarTitle(){
        setTitle(mHelper.getFarmByID(mSelectedFarmID).getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.farm_toolbar_menu, menu);
        List<Like> userLikes = mHelper.getUserLikes(mUserID);
        boolean farmLiked = false;
        for (Like like : userLikes){
            if(like.getFarmID()==mSelectedFarmID){farmLiked=true;}
        }

        if(farmLiked){
            Drawable drawable = getResources().getDrawable(R.drawable.ic_favorite_white_24dp);
            drawable.setColorFilter(Color.rgb(183, 28, 28), PorterDuff.Mode.SRC_ATOP);
            menu.findItem(R.id.favorite_button).setIcon(drawable);
        }else{
            menu.findItem(R.id.favorite_button)
                    .setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<Like> userLikes = mHelper.getUserLikes(mUserID);
        boolean farmLiked = false;
        for (Like like : userLikes){
            if(like.getFarmID()==mSelectedFarmID){farmLiked=true;}
        }
        switch (item.getItemId()){
            case R.id.favorite_button:

                if (!farmLiked) {
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_favorite_white_24dp);
                    drawable.setColorFilter(Color.rgb(183, 28, 28), PorterDuff.Mode.SRC_ATOP);
                    item.setIcon(drawable);
                    mHelper.insertLike(new Like(mSelectedFarmID, mUserID));
                }else{
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp);
                    item.setIcon(drawable);
                    mHelper.deleteLike(mHelper.getLike(mSelectedFarmID, mUserID));
                }

                mPagerAdapter.notifyDataSetChanged();

        }
        return super.onOptionsItemSelected(item);
    }


    public interface OnLikeButtonListener{
        void onLikeButtonClicked(int farmID);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        List<Food> cartItems = Cart.getInstance().getItems();
        for(Food food : cartItems){
            MySQLiteHelper.getInstance(FarmActivity.this).insertCartItem(food, mUserID);
        }
        super.onStop();
    }
}
