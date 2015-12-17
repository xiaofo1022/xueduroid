package com.xiaofo1022.xueduroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaofo1022.xueduroid.R;
import com.xiaofo1022.xueduroid.holder.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kurt.yu on 12/17/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private List<String> dataList;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        initDataList();
    }

    private void initDataList() {
        dataList = new ArrayList<>();
        dataList.add("马外");
        dataList.add("呱呱");
        dataList.add("风");
        dataList.add("失眠的时候玩什么游戏帮助入睡？");
        dataList.add("香疯了");
        dataList.add("母液");
        dataList.add("理想 歌单");
        dataList.add("薛科长喜欢喝什么酒");
        dataList.add("裸足上脚");
        dataList.add("水曲柳儿了");
        dataList.add("小懦弱");
        dataList.add("薛哥最帅");
        dataList.add("Bullet For My Valentine");
        dataList.add("运动员扔铅球的时候为什么要仰天长啸");
        dataList.add("花伦 歌单");
        dataList.add("牙膏当胡泡儿");
        dataList.add("失格懦夫");
        dataList.add("大便冲不下去怎么办");
        dataList.add("薛哥最喜欢的现役NBA球星是谁？");
        dataList.add("生命之水");
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
