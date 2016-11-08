package com.scottlindley.farmgroceryapp.FarmActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.CustomObjects.Like;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/4/2016.
 */

public class FarmLikesRecyclerAdapter extends RecyclerView.Adapter<FarmLikesRecyclerAdapter.LikesViewHolder>{
    private List<Like> mLikes;
    private Context mContext;

    public FarmLikesRecyclerAdapter(Context context, List<Like> likes) {
        mContext = context;
        mLikes = likes;
    }

    @Override
    public LikesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.likes_recycler_item,parent,false);
        return new LikesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LikesViewHolder holder, int position) {
        holder.mUserName.setText(MySQLiteHelper.getInstance(mContext).getUserByID(mLikes.get(position).getUserID()).getName());
        holder.mUserState.setText(MySQLiteHelper.getInstance(mContext).getUserByID(mLikes.get(position).getUserID()).getState());
    }

    @Override
    public int getItemCount() {
        return mLikes.size();
    }

    public void replaceData(List<Like> likes){
        mLikes = likes;
        notifyDataSetChanged();
    }

    public class LikesViewHolder extends RecyclerView.ViewHolder{
        public TextView mUserName, mUserState;
        public LikesViewHolder(View itemView) {
            super(itemView);
            mUserName = (TextView)itemView.findViewById(R.id.user_name);
            mUserState = (TextView)itemView.findViewById(R.id.user_state);
        }
    }
}
