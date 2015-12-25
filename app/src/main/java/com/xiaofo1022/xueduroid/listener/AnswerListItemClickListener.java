package com.xiaofo1022.xueduroid.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.xiaofo1022.xueduroid.DetailActivity;
import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.model.Answer;

import java.util.List;

/**
 * Created by Xiaofo on 2015/12/25.
 */
public class AnswerListItemClickListener<T> implements AdapterView.OnItemClickListener {

    private Context context;
    private List<Answer> answerList;

    public AnswerListItemClickListener(Context context, List<T> answerList) {
        this.context = context;
        this.answerList = (List<Answer>)answerList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Answer answer = answerList.get(position);
        if (answer != null) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(GlobalConst.ANSWER_ID, String.valueOf(answer.getId()));
            context.startActivity(intent);
        }
    }
}
