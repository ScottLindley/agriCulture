package com.scottlindley.farmgroceryapp.FarmActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scottlindley.farmgroceryapp.CustomObjects.Cart;
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
    public void onBindViewHolder(ProduceViewHolder holder, final int position) {
        holder.mFoodPhoto.setImageResource(mFoods.get(position).getImageID());
        holder.mFoodName.setText(mFoods.get(position).getName());
        holder.mFoodPrice.setText("$"+Double.toString(mFoods.get(position).getPrice()));

        holder.mAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart.getInstance().getItems().add(mFoods.get(position));
                Toast.makeText(view.getContext(), mFoods.get(position).getName()+
                        " added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public class ProduceViewHolder extends RecyclerView.ViewHolder{
        public ImageView mFoodPhoto, mAddToCartButton;
        public TextView mFoodName, mFoodPrice;
        public ProduceViewHolder(View itemView) {
            super(itemView);
            mFoodName = (TextView) itemView.findViewById(R.id.food_name);
            mFoodPrice = (TextView) itemView.findViewById(R.id.food_price);
            mFoodPhoto = (ImageView) itemView.findViewById(R.id.food_image);
            mAddToCartButton = (ImageView) itemView.findViewById(R.id.add_to_cart);
        }
    }
}
