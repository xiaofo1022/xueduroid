package com.xiaofo1022.xueduroid.fragment;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.xiaofo1022.xueduroid.R;
import com.xiaofo1022.xueduroid.adapter.ContributeViewAdapter;
import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.core.TaskParam;
import com.xiaofo1022.xueduroid.listener.ContributeListItemClickListener;
import com.xiaofo1022.xueduroid.model.FansContribute;

/**
 * Created by Xiaofo on 2015/12/25.
 */
public class FansContributeListFragment extends BaseListFragment<FansContribute> {

    @Override
    protected ArrayAdapter<FansContribute> createArrayAdapter() {
        return new ContributeViewAdapter(getActivity(), R.layout.list_view_number_item, dataList);
    }

    @Override
    protected TaskParam<FansContribute> createTaskParam() {
        return new TaskParam<>(GlobalConst.BASE_URL + "contribute", FansContribute.class);
    }

    @Override
    protected AdapterView.OnItemClickListener createItemClickListener() {
        return new ContributeListItemClickListener<>(getActivity(), dataList);
    }
}
