package com.scottlindley.farmgroceryapp.CartActivity;

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
 * Created by Scott Lindley on 11/5/2016.
 */

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder>{

    List<Food> mItems;

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_recycler_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.mFoodImage.setImageResource(mItems.get(position).getImageID());
        holder.mFoodName.setText(mItems.get(position).getFarmName()+"\n"+mItems.get(position).getName());
        holder.mFoodPrice.setText("$"+mItems.get(position).getPrice());
        holder.mQuantity.setText(String.valueOf(getItemQuantity(position)));

        holder.mQuantityUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public int getItemQuantity(int position){
        String foodName = mItems.get(position).getName();
        int quantity = 0;
        for(Food f: mItems){
            if (f.getName().equals(foodName)){
                quantity++;
            }
        }
        return quantity;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        public ImageView mQuantityUp, mQuantityDown, mFoodImage;
        public TextView mQuantity, mFoodName, mFoodPrice;
        public CartViewHolder(View itemView) {
            super(itemView);
            mQuantityUp = (ImageView)itemView.findViewById(R.id.quantity_up);
            mQuantityDown = (ImageView)itemView.findViewById(R.id.quantity_down);
            mQuantity = (TextView)itemView.findViewById(R.id.quantity);
            mFoodImage = (ImageView)itemView.findViewById(R.id.food_image);
            mFoodName = (TextView)itemView.findViewById(R.id.food_name);
            mFoodPrice = (TextView)itemView.findViewById(R.id.food_price);
        }
    }
}
