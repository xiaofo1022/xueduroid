package com.xiaofo1022.xueduroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
 * Created by kurt.yu on 12/24/2015.
 */
public class SearchResultActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private List<Answer> dataList;
    private ListViewAdapter viewAdapter;
    private BackgroundServiceCaller<Answer> backgroundServiceCaller;
    private String queryText;
    private Map<String, Object> requestParam = new HashMap<>();

    public SearchResultActivity() {
        initBackgroundService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        if (intent != null) {
            queryText = intent.getStringExtra(GlobalConst.QUERY_TEXT);
            requestParam.put("title", queryText);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("搜索中...");
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

        listView = (ListView)findViewById(R.id.listview_searchresult);
        dataList = new ArrayList<>();
        viewAdapter = new ListViewAdapter(this, R.layout.list_view_item, dataList);
        listView.setAdapter(viewAdapter);
        listView.setOnItemClickListener(new AnswerListItemClickListener(this, dataList));
    }

    private void initBackgroundService() {
        backgroundServiceCaller = new BackgroundServiceCaller<>(new Handler(), new JsonCallback<Answer>() {
            @Override
            public void callback(List<Answer> result) {
                int resultCount = 0;
                if (result != null) {
                    dataList.clear();
                    for (Answer data : result) {
                        dataList.add(data);
                    }
                    resultCount = dataList.size();
                }
                toolbar.setTitle("检索结果" + resultCount + "条");
                viewAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
        backgroundServiceCaller.setRequestMethod("POST");
        backgroundServiceCaller.start();
        backgroundServiceCaller.getLooper();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backgroundServiceCaller.setRequestParam(requestParam);
                backgroundServiceCaller.sendMessage(new TaskParam<>(GlobalConst.BASE_URL + "blursearch", Answer.class));
            }
        }, 500);
    }
}
