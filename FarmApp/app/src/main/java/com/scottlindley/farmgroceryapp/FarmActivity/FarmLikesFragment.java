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
import com.scottlindley.farmgroceryapp.CustomObjects.Like;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/4/2016.
 */

public class FarmLikesFragment extends Fragment implements FarmActivity.OnLikeButtonListener{
    private Farm mSelectedFarm;
    private int mFarmID;
    private List<Like> mLikes;
    private RecyclerView mRecyclerView;
    private FarmLikesRecyclerAdapter mAdapter;
    private Context mContext;


    @Override
    public void onLikeButtonClicked(int farmID) {
        mAdapter.replaceData(MySQLiteHelper.getInstance(mContext).getLikes(farmID));
        mRecyclerView.smoothScrollToPosition(mLikes.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        return inflater.inflate(R.layout.fragment_likes,container,false);
    }

    public static Fragment newInstance(Bundle bundle){
        Fragment likeFragment = new FarmLikesFragment();
        likeFragment.setArguments(bundle);
        return likeFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        mFarmID = bundle.getInt(FarmPagerAdapter.PAGER_ADAPTER_FARM_ID);

        mSelectedFarm = MySQLiteHelper.getInstance(view.getContext()).getFarmByID(mFarmID);

        mLikes = MySQLiteHelper.getInstance(mContext).getLikes(mSelectedFarm.getID());

        mRecyclerView = (RecyclerView)view.findViewById(R.id.likes_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new FarmLikesRecyclerAdapter(mContext, mLikes);
        mRecyclerView.setAdapter(mAdapter);
    }

    public FarmActivity.OnLikeButtonListener getRecyclerListener(){
        return (FarmActivity.OnLikeButtonListener)mRecyclerView;
    }
}
