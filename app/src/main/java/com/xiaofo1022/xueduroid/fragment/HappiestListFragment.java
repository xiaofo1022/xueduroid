package com.xiaofo1022.xueduroid.fragment;

import android.widget.ArrayAdapter;

import com.xiaofo1022.xueduroid.R;
import com.xiaofo1022.xueduroid.adapter.HappiestViewAdapter;
import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.core.TaskParam;
import com.xiaofo1022.xueduroid.model.Answer;

/**
 * Created by Xiaofo on 2015/12/25.
 */
public class HappiestListFragment extends BaseListFragment<Answer> {
    @Override
    protected ArrayAdapter<Answer> createArrayAdapter() {
        return new HappiestViewAdapter(getActivity(), R.layout.list_view_number_item, dataList);
    }
    @Override
    protected TaskParam<Answer> createTaskParam() {
        return new TaskParam<>(GlobalConst.BASE_URL + "happiest", Answer.class);
    }
}
