package com.xiaofo1022.xueduroid.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xiaofo1022.xueduroid.R;

/**
 * Created by kurt.yu on 12/17/2015.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.tv_recycler_item);
    }

    public void setText(String text) {
        textView.setText(text);
    }
}
