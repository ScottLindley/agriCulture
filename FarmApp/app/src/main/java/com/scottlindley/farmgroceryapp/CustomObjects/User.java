package com.scottlindley.farmgroceryapp.CustomObjects;

/**
 * Created by Scott Lindley on 11/4/2016.
 */

public class User {
    private String mName, mState;
    private int mID;

    public User(String name, String state) {
        mName = name;
        mState = state;
    }

    public String getName() {
        return mName;
    }

    public String getState() {
        return mState;
    }
}
