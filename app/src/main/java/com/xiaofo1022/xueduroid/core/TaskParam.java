package com.xiaofo1022.xueduroid.core;

/**
 * Created by Xiaofo on 2015/12/24.
 */
public class TaskParam<T> {
    private String url;
    private Class<T> cls;

    public TaskParam(String url, Class<T> cls) {
        this.url = url;
        this.cls = cls;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class<T> getCls() {
        return cls;
    }

    public void setCls(Class<T> cls) {
        this.cls = cls;
    }
}
