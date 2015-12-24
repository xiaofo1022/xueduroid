package com.xiaofo1022.xueduroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.xiaofo1022.xueduroid.adapter.ListViewAdapter;
import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.model.Answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kurt.yu on 12/24/2015.
 */
public class SearchResultActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private List<Answer> dataList;
    private ListViewAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        if (intent != null) {
            String queryText = intent.getStringExtra(GlobalConst.QUERY_TEXT);
            Log.i("You queried this shit", queryText);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                toolbar.setTitle("检索结果0条");
                Answer answer = new Answer();
                answer.setTitle("这事儿薛科长确实是不知道啊！");
                dataList.add(answer);
                viewAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }
}
