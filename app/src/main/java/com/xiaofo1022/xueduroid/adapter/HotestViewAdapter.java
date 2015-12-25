package com.xiaofo1022.xueduroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.xiaofo1022.xueduroid.R;
import com.xiaofo1022.xueduroid.model.Answer;

import java.util.List;

/**
 * Created by Xiaofo on 2015/12/25.
 */
public class HotestViewAdapter extends ArrayAdapter<Answer> {

    private int resource;
    private List<Answer> dataList;

    public HotestViewAdapter(Context context, int resource, List<Answer> dataList) {
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
        Answer data = dataList.get(position);
        title.setText(data.getTitle());
        countNumber.setText(data.getSearchCount() + "次");
        return view;
    }
}
