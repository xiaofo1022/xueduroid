package com.xiaofo1022.xueduroid.caller;

import java.util.List;

/**
 * Created by Xiaofo on 2015/12/27.
 */
public interface JsonBackgroundCaller<T> {
    List<T> callBackground();
}
