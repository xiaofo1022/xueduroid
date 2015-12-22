package com.xiaofo1022.xueduroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.xiaofo1022.xueduroid.adapter.ViewPagerAdapter;
import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.fragment.MainFragment;
import com.xiaofo1022.xueduroid.fragment.MainListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("薛度");
        setSupportActionBar(toolbar);
        initViews();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContributeOilActivity.class);
                MainActivity.this.startActivityForResult(intent, GlobalConst.RESULT_OK);
            }
        });
    }

    private void initViews() {
        String[] viewTitles = new String[] {"随便刷", "最新榜", "最热榜", "乐疯了", "贡献榜"};
        List<Fragment> fragmentList = new ArrayList<>();
        for (String title : viewTitles) {
            MainListFragment fragment = new MainListFragment();
            fragmentList.add(fragment);
        }

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), viewTitles, fragmentList);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            boolean submitSuccess = data.getBooleanExtra(GlobalConst.SUBMIT_RESULT, false);
            if (submitSuccess) {
                Snackbar.make(viewPager, "好好干小同志，薛科长会很快审批你的请求！", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
