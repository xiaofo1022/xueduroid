package com.xiaofo1022.xueduroid.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xiaofo1022.xueduroid.R;
import com.xiaofo1022.xueduroid.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaofo on 2015/12/17.
 */
public class MainListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private List<String> dataList;

    public MainListFragment() {
        initDataList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_main, container, false);
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.listrefreshlayout);
        refreshLayout.setColorSchemeResources(R.color.background_material_dark);
        refreshLayout.setOnRefreshListener(this);
        listView = (ListView)view.findViewById(R.id.main_listview);
        listView.setAdapter(new ListViewAdapter(getActivity(), R.layout.list_view_item, dataList));
        return view;
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
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
