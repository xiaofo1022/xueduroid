package com.xiaofo1022.xueduroid.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.xiaofo1022.xueduroid.ContributeListActivity;
import com.xiaofo1022.xueduroid.DetailActivity;
import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.model.Answer;
import com.xiaofo1022.xueduroid.model.FansContribute;

import java.util.List;

/**
 * Created by Xiaofo on 2015/12/27.
 */
public class ContributeListItemClickListener<T> implements AdapterView.OnItemClickListener {

    private Context context;
    private List<FansContribute> fansContributeList;

    public ContributeListItemClickListener(Context context, List<T> fansContributeList) {
        this.context = context;
        this.fansContributeList = (List<FansContribute>)fansContributeList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FansContribute fansContribute = fansContributeList.get(position);
        if (fansContribute != null) {
            Intent intent = new Intent(context, ContributeListActivity.class);
            intent.putExtra(GlobalConst.FANS_NAME, fansContribute.getFansName());
            context.startActivity(intent);
        }
    }
}
