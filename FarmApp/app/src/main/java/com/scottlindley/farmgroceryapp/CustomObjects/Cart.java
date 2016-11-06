package com.scottlindley.farmgroceryapp.CustomObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Lindley on 11/5/2016.
 */

public class Cart {
    private List<Food> mItems;
    private Cart sCart;

    private Cart(){
        mItems = new ArrayList<>();
    }

    public Cart getInstance(){
        if(sCart==null){
            sCart = new Cart();
        }
        return sCart;
    }

    public List<Food> getItems() {
        return mItems;
    }
}
