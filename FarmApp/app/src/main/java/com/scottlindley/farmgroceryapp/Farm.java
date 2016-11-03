package com.scottlindley.farmgroceryapp;

import java.util.List;

import static android.R.attr.id;

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
        mID = id;
        mName = name;
        mStory = story;
        mState = state;
        mSpecialty = specialty;


        switch (mName) {
            case "McDonald Farms":
                mPhotoID = (R.drawable.mcdonald);
                break;
            case "Stony Hill Farms":
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
            case "Turnup the Beet":
                mPhotoID = (R.drawable.beet);
                break;
            case "Scare Crow Farms":
                mPhotoID = (R.drawable.scarecrow);
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
}
