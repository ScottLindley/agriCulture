package com.scottlindley.farmgroceryapp.OrderHistoryActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CustomObjects.Order;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;


/**
 * Created by Scott Lindley on 11/8/2016.
 */

public class OrderHistoryRecyclerAdapter extends RecyclerView.Adapter<OrderHistoryRecyclerAdapter.OrderHistoryViewHolder>{

    private List<Order> mOrders;
    private Context mContext;

    public OrderHistoryRecyclerAdapter(List<Order> orders, Context context) {
        mOrders = orders;
        mContext = context;
    }

    @Override
    public OrderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_recycler_item,parent,false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHistoryViewHolder holder, int position) {
        holder.mOrderPrice.setText("$"+mOrders.get(position).getOrderPrice());
        holder.mOrderDate.setText(mOrders.get(position).getOrderDate());
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView mOrderPrice, mOrderDate;
        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            mOrderDate = (TextView)itemView.findViewById(R.id.order_date);
            mOrderPrice = (TextView)itemView.findViewById(R.id.order_price);
        }
    }
}
