package com.scottlindley.farmgroceryapp;

/**
 * Created by Scott Lindley on 11/2/2016.
 */

public class Like {
    private int mID;
    private int mFarmID;
    private int mUserID;

    public Like(int ID, int farmID, int userID) {
        mID = ID;
        mFarmID = farmID;
        mUserID = userID;
    }
}
