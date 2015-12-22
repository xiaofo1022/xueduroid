package com.xiaofo1022.xueduroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Xiaofo on 2015/12/22.
 */
public class SupplementActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView supplementTitle;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的补充");
        setSupportActionBar(toolbar);

        supplementTitle = (TextView)findViewById(R.id.tv_supplement_title);
        supplementTitle.setText("关于：孕妇轻度饮酒可能会引起孩子哪些症状？ 的补充");

        submitButton = (Button)findViewById(R.id.btn_submit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
}
