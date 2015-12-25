package com.xiaofo1022.xueduroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.xiaofo1022.xueduroid.R;
import com.xiaofo1022.xueduroid.model.FansContribute;

import java.util.List;

/**
 * Created by Xiaofo on 2015/12/25.
 */
public class ContributeViewAdapter extends ArrayAdapter<FansContribute> {

    private int resource;
    private List<FansContribute> dataList;

    public ContributeViewAdapter(Context context, int resource, List<FansContribute> dataList) {
        super(context, resource, dataList);
        this.resource = resource;
        this.dataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        } else {
            view = convertView;
        }
        TextView title = (TextView)view.findViewById(R.id.tv_list_item);
        TextView countNumber = (TextView)view.findViewById(R.id.tv_count_number);
        FansContribute data = dataList.get(position);
        title.setText(data.getFansName());
        countNumber.setText(data.getContributeCount() + "斤");
        return view;
    }
}
