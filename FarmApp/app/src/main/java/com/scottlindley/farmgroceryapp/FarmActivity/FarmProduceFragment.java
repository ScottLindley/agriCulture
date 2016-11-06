package com.scottlindley.farmgroceryapp.FarmActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.CustomObjects.Farm;
import com.scottlindley.farmgroceryapp.CustomObjects.Food;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/4/2016.
 */

public class FarmProduceFragment extends Fragment {
    private Farm mSelectedFarm;
    private RecyclerView mRecyclerView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        return inflater.inflate(R.layout.fragment_farm_produce, container, false);
    }

    public static Fragment newInstance(Bundle bundle){
        Fragment productFragment = new FarmProduceFragment();
        productFragment.setArguments(bundle);
        return productFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        int farmID = bundle.getInt(FarmPagerAdapter.PAGER_ADAPTER_FARM_ID);

        mSelectedFarm = MySQLiteHelper.getInstance(view.getContext()).getFarmByID(farmID);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.produce_fragment_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        List<Food> foods = MySQLiteHelper.getInstance(mContext).getFoodByFarm(mSelectedFarm.getID());

        mRecyclerView.setAdapter(new FarmProduceRecyclerViewAdapter(foods));
    }
}
