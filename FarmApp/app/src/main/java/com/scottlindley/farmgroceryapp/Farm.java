package com.scottlindley.farmgroceryapp;

import java.util.List;

/**
 * Created by Scott Lindley on 11/1/2016.
 */

public class Farm {
    private String mName, mStory, mState, mSpecialty;
    private int mID;
    private int mPhotoID;
    private List<Like> mNumLikes;
    private List<Food> mInventory;

    public Farm(int ID, String name, String story, String specialty, String state) {
        mID = ID;
        mName = name;
        mStory = story;
        mState = state;
        mSpecialty = specialty;

        switch (mName) {
            case "McDonald Farms":
                mPhotoID = (R.drawable.mcdonald);
                break;
            case "Stoney Hill Farms":
                mPhotoID = (R.drawable.stony_hill);
                break;
            case "Sunny Side Farms":
                mPhotoID = (R.drawable.sunny);
                break;
            case "Seed Sowers":
                mPhotoID = (R.drawable.sowers);
                break;
            case "The Funny Farm":
                mPhotoID = (R.drawable.funny);
                break;
            case "Turnip the Beet":
                mPhotoID = (R.drawable.beet);
                break;
            case "Scare Crow Farms":
                mPhotoID = (R.drawable.scarecrow);
                break;
            case "Couch Potato Farms":
                mPhotoID = (R.drawable.couch_potato);
                break;
            case "Peter Piper Farms":
                mPhotoID = (R.drawable.pickled_peppers);
                break;
            case "Strawberry Fields":
                mPhotoID = (R.drawable.strawberry_fields);
                break;
            case "Buffalo Hill Gardens":
                mPhotoID = (R.drawable.buffalo_hills);
                break;
            case "Deer Cove Acres":
                mPhotoID = (R.drawable.deer_cove);
                break;
            case "Angry Beaver Nursery":
                mPhotoID = (R.drawable.angry_beaver);
                break;
            case "Big Bear Orchard":
                mPhotoID = (R.drawable.bear_orchard);
                break;
            case "Bumble Bee Lands":
                mPhotoID = (R.drawable.bee_land);
                break;
            case "Flower Hills Range":
                mPhotoID = (R.drawable.flower_hill);
                break;
        }
    }

    public String getName() {
        return mName;
    }

    public String getStory() {
        return mStory;
    }

    public String getState() {
        return mState;
    }

    public String getSpecialty() {
        return mSpecialty;
    }

    public List<Like> getNumLikes() {
        return mNumLikes;
    }

    public int getID() {
        return mID;
    }

    public int getPhotoID(){
        return mPhotoID;
    }

    public void setNumLikes(List<Like> numLikes) {
        mNumLikes = numLikes;
    }

    public void setInventory(List<Food> inventory) {
        mInventory = inventory;
    }
}
