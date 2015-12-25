package com.xiaofo1022.xueduroid.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xiaofo1022.xueduroid.R;
import com.xiaofo1022.xueduroid.core.TaskParam;
import com.xiaofo1022.xueduroid.listener.AnswerListItemClickListener;
import com.xiaofo1022.xueduroid.thread.BackgroundServiceCaller;
import com.xiaofo1022.xueduroid.thread.JsonCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaofo on 2015/12/25.
 */
public abstract class BaseListFragment<T> extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private ArrayAdapter<T> viewAdapter;
    private BackgroundServiceCaller<T> backgroundServiceCaller;
    protected List<T> dataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_main, container, false);
        initBackgroundService();
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.listrefreshlayout);
        refreshLayout.setColorSchemeResources(R.color.background_material_dark);
        refreshLayout.setOnRefreshListener(this);
        if (dataList.size() == 0) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                    onRefresh();
                }
            });
        }
        listView = (ListView)view.findViewById(R.id.main_listview);
        viewAdapter = createArrayAdapter();
        listView.setAdapter(viewAdapter);
        listView.setOnItemClickListener(createItemClickListener());
    }

    protected AdapterView.OnItemClickListener createItemClickListener() {
        return new AnswerListItemClickListener(getActivity(), dataList);
    }

    private void initBackgroundService() {
        backgroundServiceCaller = new BackgroundServiceCaller<>(new Handler(), new JsonCallback<T>() {
            @Override
            public void callback(List<T> result) {
                if (result != null) {
                    dataList.clear();
                    for (T data : result) {
                        dataList.add(data);
                    }
                }
                viewAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
        backgroundServiceCaller.start();
        backgroundServiceCaller.getLooper();
    }

    @Override
    public void onRefresh() {
        backgroundServiceCaller.sendMessage(createTaskParam());
    }

    protected abstract ArrayAdapter<T> createArrayAdapter();
    protected  abstract TaskParam<T> createTaskParam();
}
