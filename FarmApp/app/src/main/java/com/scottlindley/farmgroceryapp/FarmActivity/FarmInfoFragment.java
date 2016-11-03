package com.scottlindley.farmgroceryapp.FarmActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.Farm;
import com.scottlindley.farmgroceryapp.R;

/**
 * Created by Scott Lindley on 11/2/2016.
 */

public class FarmInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_farm_info,container,false);
    }

    public static Fragment newInstance(Bundle bundle){
        Fragment infoFragment = new FarmInfoFragment();
        infoFragment.setArguments(bundle);
        return infoFragment;
    }

    public FarmInfoFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        int farmID = bundle.getInt(FarmPagerAdapter.PAGER_ADAPTER_FARM_ID);

        Farm selectedFarm = MySQLiteHelper.getInstance(view.getContext()).getFarmByID(farmID);

        ImageView photo = (ImageView)view.findViewById(R.id.farm_photo);
        TextView name = (TextView)view.findViewById(R.id.farm_name);
        TextView story = (TextView)view.findViewById(R.id.farm_story);
        TextView state = (TextView)view.findViewById(R.id.farm_state);

        Log.d("TAG", "onViewCreated: "+farmID);
        photo.setImageResource(selectedFarm.getPhotoID());
        story.setText(selectedFarm.getStory());
    }
}
