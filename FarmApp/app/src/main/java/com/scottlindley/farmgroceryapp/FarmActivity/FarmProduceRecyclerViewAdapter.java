package com.scottlindley.farmgroceryapp.FarmActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CustomObjects.Food;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/4/2016.
 */

public class FarmProduceRecyclerViewAdapter extends RecyclerView.Adapter<FarmProduceRecyclerViewAdapter.ProduceViewHolder>{
    private List<Food> mFoods;

    public FarmProduceRecyclerViewAdapter(List<Food> foods) {
        mFoods = foods;
    }

    @Override
    public ProduceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produce_recycler_items, parent, false);
        return new ProduceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProduceViewHolder holder, int position) {
        holder.mFoodPhoto.setImageResource(mFoods.get(position).getImageID());
        holder.mFoodName.setText(mFoods.get(position).getName());
        holder.mFoodPrice.setText("$"+Double.toString(mFoods.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public class ProduceViewHolder extends RecyclerView.ViewHolder{
        public ImageView mFoodPhoto;
        public TextView mFoodName, mFoodPrice;
        public ProduceViewHolder(View itemView) {
            super(itemView);
            mFoodName = (TextView) itemView.findViewById(R.id.food_name);
            mFoodPrice = (TextView) itemView.findViewById(R.id.food_price);
            mFoodPhoto = (ImageView) itemView.findViewById(R.id.food_image);
        }
    }
}
