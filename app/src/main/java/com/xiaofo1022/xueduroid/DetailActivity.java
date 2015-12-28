package com.xiaofo1022.xueduroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.core.TaskParam;
import com.xiaofo1022.xueduroid.model.Answer;
import com.xiaofo1022.xueduroid.model.SupplementAnswer;
import com.xiaofo1022.xueduroid.thread.BackgroundServiceCaller;
import com.xiaofo1022.xueduroid.thread.JsonCallback;

import java.util.List;

/**
 * Created by kurt.yu on 12/21/2015.
 */
public class DetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private Toolbar toolbar;
    private TextView answerTitle;
    private LinearLayout layoutMain;
    private Button laughCrazyButton;
    private Button mySupplementButton;
    private boolean isLaughed = false;
    private String answerId;
    private Answer answer;
    private BackgroundServiceCaller<Answer> backgroundServiceCaller;
    private BackgroundServiceCaller<String> increaseSearchCaller;
    private BackgroundServiceCaller<String> increaseHappyCaller;

    public DetailActivity() {
        initBackgroundService();
        initSearchResultService();
        initHappyCountService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null) {
            answerId = intent.getStringExtra(GlobalConst.ANSWER_ID);
        }

        increaseSearchResult();

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

        layoutMain = (LinearLayout)findViewById(R.id.detail_main);
        answerTitle = (TextView)findViewById(R.id.tv_answer_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mySupplementButton = (Button)findViewById(R.id.btn_my_supplement);
        mySupplementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer != null) {
                    Intent intent = new Intent(DetailActivity.this, SupplementActivity.class);
                    intent.putExtra(GlobalConst.ANSWER_ID, answer.getId());
                    intent.putExtra(GlobalConst.SUPPLEMENT_TITLE, answer.getTitle());
                    startActivityForResult(intent, GlobalConst.RESULT_OK);
                }
            }
        });

        laughCrazyButton = (Button)findViewById(R.id.btn_laugh_crazy);
        laughCrazyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLaughed) {
                    isLaughed = true;
                    increaseHappyCount();
                    Toast.makeText(DetailActivity.this, "乐疯了 +1", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, "乐呵乐呵得了", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initBackgroundService() {
        backgroundServiceCaller = new BackgroundServiceCaller<>(new Handler(), new JsonCallback<Answer>() {
            @Override
            public void callback(List<Answer> result) {
                if (result != null) {
                    answer = result.get(0);
                    if (answer != null) {
                        layoutMain.removeAllViews();
                        toolbar.setTitle(answer.getTitle());
                        answerTitle.setText(answer.getTitle());
                        String contributor = "";
                        if (answer.getFansAnswer() != null) {
                            contributor = "--- 来自:" + answer.getFansAnswer().getFansName() + "的贡献";
                        }
                        layoutMain.addView(createInfoView(answer.getAnswer(), contributor));
                        List<SupplementAnswer> supplementAnswerList = answer.getSupplementAnswerList();
                        if (supplementAnswerList != null && supplementAnswerList.size() > 0) {
                            for (SupplementAnswer supplementAnswer : supplementAnswerList) {
                                layoutMain.addView(createInfoView(supplementAnswer.getAnswer(), "--- 来自:" + supplementAnswer.getFansName() + "的补充"));
                            }
                        }
                    }
                }
                refreshLayout.setRefreshing(false);
            }
        });
        backgroundServiceCaller.start();
        backgroundServiceCaller.getLooper();
    }

    private void initSearchResultService() {
        increaseSearchCaller = new BackgroundServiceCaller<>(new Handler(), new JsonCallback<String>() {
            @Override
            public void callback(List<String> result) {
                if (result != null && result.size() > 0) {
                    Log.i("DetailActivity", "increaseSearchResult: " + result.get(0));
                } else {
                    Log.i("DetailActivity", "increaseSearchResult: Fail");
                }
            }
        });
        increaseSearchCaller.setRequestMethod("POST");
        increaseSearchCaller.start();
        increaseSearchCaller.getLooper();
    }

    private void initHappyCountService() {
        increaseHappyCaller = new BackgroundServiceCaller<>(new Handler(), new JsonCallback<String>() {
            @Override
            public void callback(List<String> result) {
                if (result != null && result.size() > 0) {
                    Log.i("DetailActivity", "increaseHappyCount: " + result.get(0));
                } else {
                    Log.i("DetailActivity", "increaseHappyCount: Fail");
                }
            }
        });
        increaseHappyCaller.setRequestMethod("POST");
        increaseHappyCaller.start();
        increaseHappyCaller.getLooper();
    }

    private void increaseSearchResult() {
        increaseSearchCaller.sendMessage(new TaskParam<>(GlobalConst.BASE_URL + "increasesearch/" + answerId, String.class));
    }

    private void increaseHappyCount() {
        increaseHappyCaller.sendMessage(new TaskParam<>(GlobalConst.BASE_URL + "increasehappy/" + answerId, String.class));
    }

    private View createInfoView(String info, String contributor) {
        LinearLayout view = (LinearLayout)LayoutInflater.from(this).inflate(R.layout.item_answer_info, null);

        //TextView textInfo = (TextView)view.findViewById(R.id.tv_answer_info);
        //textInfo.setText(info);

        String[] infoLines = info.split("\n");
        for (String infoLine : infoLines) {
            TextView text = (TextView)LayoutInflater.from(this).inflate(R.layout.textview_answer_info, null);
            text.setText(infoLine);
            if (infoLine.indexOf("http") >= 0) {
                text.setAutoLinkMask(Linkify.ALL);
                text.setMovementMethod(LinkMovementMethod.getInstance());
            }
            view.addView(text);
        }

        TextView textContributor = (TextView)LayoutInflater.from(this).inflate(R.layout.textview_answer_contributor, null);
        if (contributor != null && !contributor.equals("")) {
            textContributor.setText(contributor);
            view.addView(textContributor);
        }
        return view;
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
    protected void onStop() {
        super.onStop();
        isLaughed = false;
    }

    @Override
    public void onRefresh() {
        if (answerId != null && !answerId.equals("")) {
            backgroundServiceCaller.sendMessage(new TaskParam<>(GlobalConst.BASE_URL + "getanswer/" + answerId, Answer.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            boolean submitSuccess = data.getBooleanExtra(GlobalConst.SUBMIT_RESULT, false);
            if (submitSuccess) {
                Snackbar.make(refreshLayout, "好好干小同志，薛科长会很快审批你的请求！", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
