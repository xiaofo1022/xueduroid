package com.xiaofo1022.xueduroid.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xiaofo1022.xueduroid.DetailActivity;
import com.xiaofo1022.xueduroid.R;
import com.xiaofo1022.xueduroid.adapter.ListViewAdapter;
import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.core.JsonUtil;
import com.xiaofo1022.xueduroid.core.TaskParam;
import com.xiaofo1022.xueduroid.model.Answer;
import com.xiaofo1022.xueduroid.thread.BackgroundServiceCaller;
import com.xiaofo1022.xueduroid.thread.JsonCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaofo on 2015/12/17.
 */
public class MainListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private List<Answer> dataList = new ArrayList<>();
    private ListViewAdapter viewAdapter;
    private BackgroundServiceCaller<Answer> backgroundServiceCaller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_main, container, false);

        initBackgroundService();

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.listrefreshlayout);
        refreshLayout.setColorSchemeResources(R.color.background_material_dark);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                onRefresh();
            }
        });

        listView = (ListView)view.findViewById(R.id.main_listview);
        viewAdapter = new ListViewAdapter(getActivity(), R.layout.list_view_item, dataList);
        listView.setAdapter(viewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    private void initBackgroundService() {
        backgroundServiceCaller = new BackgroundServiceCaller<>(new Handler(), new JsonCallback<Answer>() {
            @Override
            public void callback(List<Answer> result) {
                if (result != null) {
                    dataList = result;
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
        backgroundServiceCaller.sendMessage(new TaskParam<>(GlobalConst.BASE_URL + "shuffle", Answer.class));
    }

    private class GetAnswerListTask extends AsyncTask<TaskParam<Answer>, Void, List<Answer>> {
        @Override
        protected void onPostExecute(List<Answer> answers) {
            if (answers != null) {
                dataList = answers;
            }
            listView.setAdapter(viewAdapter);
            viewAdapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        }
        @Override
        protected List<Answer> doInBackground(TaskParam<Answer>... params) {
            TaskParam<Answer> param = params[0];
            List<Answer> dataList = JsonUtil.getDataList(param.getUrl(), param.getCls());
            return dataList;
        }
    }
}
