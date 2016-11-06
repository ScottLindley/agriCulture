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

    public User(String name, String state, int ID) {
        mName = name;
        mState = state;
        mID = ID;
    }

    public String getName() {
        return mName;
    }

    public String getState() {
        return mState;
    }

    public int getID() {
        return mID;
    }
}
