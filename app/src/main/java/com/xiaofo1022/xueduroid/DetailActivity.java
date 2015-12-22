package com.xiaofo1022.xueduroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kurt.yu on 12/21/2015.
 */
public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView answerTitle;
    private LinearLayout layoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("孕妇轻度饮酒可能会引起孩子哪些症状？");
        setSupportActionBar(toolbar);

        answerTitle = (TextView)findViewById(R.id.tv_answer_title);
        answerTitle.setText("孕妇轻度饮酒可能会引起孩子哪些症状？");

        layoutMain = (LinearLayout)findViewById(R.id.detail_main);
        layoutMain.addView(createInfoView("过动、无法专心，以及冲动、注意力持续时间短，行为与患有注意力缺陷障碍的儿童相似，症状参考彪子，咱俩看会儿电视就干别的去了。", ""));
        layoutMain.addView(createInfoView("就俩人！张学友！周慧敏！", "--- 来自:MathLotus乐队小刘的贡献"));
        layoutMain.addView(createInfoView("港台四大丧逼，王杰为首", "--- 来自:来自共慈园的热心听众的补充"));
        layoutMain.addView(createInfoView("如果有人说“老师我知道是他干的！”薛哥下课还得捎带手儿主持一下正义！", "--- 来自:SOSO神经女青年的补充"));
    }

    private View createInfoView(String info, String contributor) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_answer_info, null);
        TextView textInfo = (TextView)view.findViewById(R.id.tv_answer_info);
        textInfo.setText(info);
        TextView textContributor = (TextView)view.findViewById(R.id.tv_answer_contributor);
        if (contributor == null || contributor.equals("")) {
            textContributor.setVisibility(View.INVISIBLE);
        } else {
            textContributor.setText(contributor);
        }
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
}
