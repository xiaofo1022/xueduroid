package com.xiaofo1022.xueduroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.xiaofo1022.xueduroid.R;

import java.util.List;

/**
 * Created by Xiaofo on 2015/12/17.
 */
public class ListViewAdapter extends ArrayAdapter<String> {

    private int resource;
    private List<String> dataList;

    public ListViewAdapter(Context context, int resource, List<String> dataList) {
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
        TextView tv = (TextView)view.findViewById(R.id.tv_list_item);
        tv.setText(dataList.get(position));
        return view;
    }
}
