package com.xiaofo1022.xueduroid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.core.TaskParam;
import com.xiaofo1022.xueduroid.thread.BackgroundServiceCaller;
import com.xiaofo1022.xueduroid.thread.JsonCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by kurt.yu on 12/22/2015.
 */
public class ContributeOilActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button submitButton;
    private EditText fansNameText;
    private EditText titleText;
    private EditText answerText;
    private BackgroundServiceCaller<String> contributeOilCaller;

    public ContributeOilActivity() {
        initContributeOilService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute_oil);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我为迪吧献石油");
        setSupportActionBar(toolbar);

        fansNameText = (EditText)findViewById(R.id.et_contributor_name);
        titleText = (EditText)findViewById(R.id.et_title);
        answerText = (EditText)findViewById(R.id.et_info);

        submitButton = (Button)findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contributeCheck()) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ContributeOilActivity.this);
                    alertBuilder.setTitle("提示");
                    alertBuilder.setMessage("是否确认提交？");
                    alertBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            contributeOil();
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
            }
        });
    }

    private boolean contributeCheck() {
        if (fansNameText.getText() == null || fansNameText.getText().toString().trim().equals("")) {
            Toast.makeText(this, "你是谁？", Toast.LENGTH_SHORT).show();
            fansNameText.requestFocus();
            return false;
        }
        if (titleText.getText() == null || titleText.getText().toString().trim().equals("")) {
            Toast.makeText(this, "你要说？", Toast.LENGTH_SHORT).show();
            titleText.requestFocus();
            return false;
        }
        if (answerText.getText() == null || answerText.getText().toString().trim().equals("")) {
            Toast.makeText(this, "是什么？", Toast.LENGTH_SHORT).show();
            answerText.requestFocus();
            return false;
        }
        return true;
    }

    private void contributeOil() {
        submitButton.setEnabled(false);

        String fansName = fansNameText.getText().toString().trim();
        String title = titleText.getText().toString().trim();
        String answer = answerText.getText().toString().trim();

        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("fansName", fansName);
        requestParam.put("title", title);
        requestParam.put("answer", answer);

        contributeOilCaller.setRequestParam(requestParam);
        contributeOilCaller.sendMessage(new TaskParam<>(GlobalConst.BASE_URL + "addfansanswer/", String.class));
    }

    private void initContributeOilService() {
        contributeOilCaller = new BackgroundServiceCaller<>(new Handler(), new JsonCallback<String>() {
            @Override
            public void callback(List<String> result) {
                if (result != null && result.size() > 0) {
                    Log.i("ContributeOilActivity", "initContributeOilService: " + result.get(0));
                    Intent intent = new Intent();
                    intent.putExtra(GlobalConst.SUBMIT_RESULT, true);
                    ContributeOilActivity.this.setResult(GlobalConst.RESULT_OK, intent);
                    ContributeOilActivity.this.finish();
                } else {
                    Log.i("ContributeOilActivity", "initContributeOilService: Fail");
                    Toast.makeText(ContributeOilActivity.this, "服务器可能出了一点小问题", Toast.LENGTH_SHORT).show();
                }
            }
        });
        contributeOilCaller.setRequestMethod("POST");
        contributeOilCaller.start();
        contributeOilCaller.getLooper();
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
}
