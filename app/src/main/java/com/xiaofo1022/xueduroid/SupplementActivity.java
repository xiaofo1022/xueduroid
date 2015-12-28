package com.xiaofo1022.xueduroid;

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
import android.widget.TextView;
import android.widget.Toast;

import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.core.TaskParam;
import com.xiaofo1022.xueduroid.thread.BackgroundServiceCaller;
import com.xiaofo1022.xueduroid.thread.JsonCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiaofo on 2015/12/22.
 */
public class SupplementActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView supplementTitle;
    private Button submitButton;
    private EditText fansNameText;
    private EditText answerText;
    private int answerId;
    private String title = "";
    private BackgroundServiceCaller<String> supplementCaller;

    public SupplementActivity() {
        initSupplementService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement);

        Intent intent = getIntent();
        if (intent != null) {
            answerId = intent.getIntExtra(GlobalConst.ANSWER_ID, -1);
            title = intent.getStringExtra(GlobalConst.SUPPLEMENT_TITLE);
        }

        fansNameText = (EditText)findViewById(R.id.et_contributor_name);
        answerText = (EditText)findViewById(R.id.et_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的补充");
        setSupportActionBar(toolbar);

        supplementTitle = (TextView)findViewById(R.id.tv_supplement_title);
        supplementTitle.setText("关于：" + title + " 的补充");

        submitButton = (Button)findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (supplementCheck()) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SupplementActivity.this);
                    alertBuilder.setTitle("提示");
                    alertBuilder.setMessage("是否确认提交？");
                    alertBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doSupplement();
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

    private boolean supplementCheck() {
        if (fansNameText.getText() == null || fansNameText.getText().toString().trim().equals("")) {
            Toast.makeText(this, "你是谁？", Toast.LENGTH_SHORT).show();
            fansNameText.requestFocus();
            return false;
        }
        if (answerText.getText() == null || answerText.getText().toString().trim().equals("")) {
            Toast.makeText(this, "你的补充？", Toast.LENGTH_SHORT).show();
            answerText.requestFocus();
            return false;
        }
        return true;
    }

    private void doSupplement() {
        submitButton.setEnabled(false);

        String fansName = fansNameText.getText().toString().trim();
        String answer = answerText.getText().toString().trim();

        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("answerId", String.valueOf(answerId));
        requestParam.put("fansName", fansName);
        requestParam.put("title", title);
        requestParam.put("answer", answer);

        supplementCaller.setRequestParam(requestParam);
        supplementCaller.sendMessage(new TaskParam<>(GlobalConst.BASE_URL + "addsupplementanswer/", String.class));
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
        } else if (item.getItemId() == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSupplementService() {
        supplementCaller = new BackgroundServiceCaller<>(new Handler(), new JsonCallback<String>() {
            @Override
            public void callback(List<String> result) {
                if (result != null && result.size() > 0) {
                    Log.i("SupplementActivity", "initSupplementService: " + result.get(0));
                    Intent intent = new Intent();
                    intent.putExtra(GlobalConst.SUBMIT_RESULT, true);
                    SupplementActivity.this.setResult(GlobalConst.RESULT_OK, intent);
                    SupplementActivity.this.finish();
                } else {
                    Log.i("SupplementActivity", "initSupplementService: Fail");
                    Toast.makeText(SupplementActivity.this, "服务器可能出了一点小问题", Toast.LENGTH_SHORT).show();
                }
            }
        });
        supplementCaller.setRequestMethod("POST");
        supplementCaller.start();
        supplementCaller.getLooper();
    }
}
