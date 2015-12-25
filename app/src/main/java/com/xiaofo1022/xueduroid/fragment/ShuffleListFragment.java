package com.xiaofo1022.xueduroid.fragment;

import android.widget.ArrayAdapter;

import com.xiaofo1022.xueduroid.R;
import com.xiaofo1022.xueduroid.adapter.ListViewAdapter;
import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.core.TaskParam;
import com.xiaofo1022.xueduroid.model.Answer;

/**
 * Created by Xiaofo on 2015/12/25.
 */
public class ShuffleListFragment extends BaseListFragment<Answer> {
    @Override
    protected ArrayAdapter<Answer> createArrayAdapter() {
        return new ListViewAdapter(getActivity(), R.layout.list_view_item, dataList);
    }
    @Override
    protected TaskParam<Answer> createTaskParam() {
        return new TaskParam<>(GlobalConst.BASE_URL + "shuffle", Answer.class);
    }
}
