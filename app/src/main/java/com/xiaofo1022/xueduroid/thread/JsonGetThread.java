package com.xiaofo1022.xueduroid.thread;

import com.xiaofo1022.xueduroid.core.JsonUtil;

import java.util.List;

/**
 * Created by Xiaofo on 2015/12/24.
 */
public class JsonGetThread<T> implements Runnable {

    private JsonCallback callback;
    private String url;
    private Class<T> cls;

    public JsonGetThread(String url, Class<T> cls, JsonCallback callback) {
        this.url = url;
        this.cls = cls;
        this.callback = callback;
    }

    @Override
    public void run() {
        List<T> dataList = JsonUtil.getDataList(url, cls);
        callback.callback(dataList);
    }
}
