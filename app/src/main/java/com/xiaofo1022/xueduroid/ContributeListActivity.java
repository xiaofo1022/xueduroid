package com.xiaofo1022.xueduroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ListView;

import com.xiaofo1022.xueduroid.adapter.ListViewAdapter;
import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.core.TaskParam;
import com.xiaofo1022.xueduroid.listener.AnswerListItemClickListener;
import com.xiaofo1022.xueduroid.model.Answer;
import com.xiaofo1022.xueduroid.thread.BackgroundServiceCaller;
import com.xiaofo1022.xueduroid.thread.JsonCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiaofo on 2015/12/27.
 */
public class ContributeListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private List<Answer> dataList;
    private ListViewAdapter viewAdapter;
    private BackgroundServiceCaller<Answer> backgroundServiceCaller;
    private String fansName = "";
    private Map<String, Object> requestParam = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute_list);

        initBackgroundService();

        Intent intent = getIntent();
        if (intent != null) {
            fansName = intent.getStringExtra(GlobalConst.FANS_NAME);
            requestParam.put("fansName", fansName);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(fansName + "的贡献");
        setSupportActionBar(toolbar);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.listrefreshlayout);
        refreshLayout.setColorSchemeResources(R.color.background_material_dark);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                onRefresh();
            }
        });

        listView = (ListView)findViewById(R.id.listview_contribute);
        dataList = new ArrayList<>();
        viewAdapter = new ListViewAdapter(this, R.layout.list_view_item, dataList);
        listView.setAdapter(viewAdapter);
        listView.setOnItemClickListener(new AnswerListItemClickListener(this, dataList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    private void initBackgroundService() {
        backgroundServiceCaller = new BackgroundServiceCaller<>(new Handler(), new JsonCallback<Answer>() {
            @Override
            public void callback(List<Answer> result) {
                if (result != null) {
                    dataList.clear();
                    for (Answer data : result) {
                        dataList.add(data);
                    }
                }
                viewAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
        backgroundServiceCaller.setRequestMethod("POST");
        backgroundServiceCaller.setRequestParam(requestParam);
        backgroundServiceCaller.start();
        backgroundServiceCaller.getLooper();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backgroundServiceCaller.sendMessage(new TaskParam<>(GlobalConst.BASE_URL + "answerlistByFans", Answer.class));
            }
        }, 500);
    }
}
