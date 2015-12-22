package com.xiaofo1022.xueduroid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.xiaofo1022.xueduroid.core.GlobalConst;

/**
 * Created by kurt.yu on 12/22/2015.
 */
public class ContributeOilActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute_oil);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我为迪吧献石油");
        setSupportActionBar(toolbar);

        submitButton = (Button)findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ContributeOilActivity.this);
                alertBuilder.setTitle("提示");
                alertBuilder.setMessage("是否确认提交？");
                alertBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(GlobalConst.SUBMIT_RESULT, true);
                        ContributeOilActivity.this.setResult(GlobalConst.RESULT_OK, intent);
                        ContributeOilActivity.this.finish();
                    }
                });
                alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertBuilder.create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
}
