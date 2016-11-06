package com.scottlindley.farmgroceryapp.CustomObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Lindley on 11/5/2016.
 */

public class Cart {
    private List<Food> mItems;
    private static Cart sCart;

    private Cart(){
        mItems = new ArrayList<>();
    }

    /*
    This method is called when the cart activity calls onResume(); It goes through the cart and
    removes any item with a quantity of zero. This allows a user to decrement a cart item down
    to zero without the item disappearing. However, one the user leaves and then returns to the
    cart activity, the item will be gone.
     */
    public void removeZeroQuantities(){
        List<Food> revisedList = new ArrayList<>();
        for(Food food : mItems){
            if(food.getQuantity()!=0){
                revisedList.add(food);
            }
        }
        mItems = revisedList;
    }

    public static Cart getInstance(){
        if(sCart==null){
            sCart = new Cart();
        }
        return sCart;
    }

    public List<Food> getItems() {
        return mItems;
    }
}
