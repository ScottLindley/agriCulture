package com.scottlindley.farmgroceryapp.FarmActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.FarmList.FarmListActivity;
import com.scottlindley.farmgroceryapp.FarmList.FarmListRecyclerAdapter;
import com.scottlindley.farmgroceryapp.R;

import static com.scottlindley.farmgroceryapp.R.id.toolbar;

public class FarmActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int mSelectedFarmID;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);
        mToolbar = (Toolbar) findViewById(toolbar);
        setSupportActionBar(mToolbar);

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

        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_order_history) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_farm_list){
            Intent intent = new Intent(this, FarmListActivity.class);
            startActivity(intent);
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
                Snackbar.make(view, "This does nothing yet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
    }

    public void setUpViewPagerAndTabs(){
        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        FarmPagerAdapter pagerAdapter = new FarmPagerAdapter(getSupportFragmentManager(), mSelectedFarmID);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setToolBarTitle(){
        setTitle(MySQLiteHelper.getInstance(FarmActivity.this).getFarmByID(mSelectedFarmID).getName());
    }
}
