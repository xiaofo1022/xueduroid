package com.xiaofo1022.xueduroid.thread;

import java.util.List;

/**
 * Created by Xiaofo on 2015/12/24.
 */
public interface JsonCallback<T> {
    void callback(List<T> result);
}
