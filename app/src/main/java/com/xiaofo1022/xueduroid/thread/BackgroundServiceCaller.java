package com.xiaofo1022.xueduroid.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.xiaofo1022.xueduroid.core.GlobalConst;
import com.xiaofo1022.xueduroid.core.JsonUtil;
import com.xiaofo1022.xueduroid.core.TaskParam;

import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;

/**
 * Created by kurt.yu on 12/25/2015.
 */
public class BackgroundServiceCaller<T> extends HandlerThread {

    private Handler handler;
    private Handler responseHandler;
    private JsonCallback<T> callback;
    private String requestMethod = "GET";
    private Map<String, Object> requestParam;

    public BackgroundServiceCaller(Handler responseHandler, JsonCallback<T> callback) {
        super("BackgroundServiceCaller");
        this.responseHandler = responseHandler;
        this.callback = callback;
        handler = new Handler();
    }

    @Override
    protected void onLooperPrepared() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == GlobalConst.CALL_BACKGROUND_SERVICE) {
                    TaskParam<T> param = (TaskParam<T>)msg.obj;
                    List<T> dataList = null;
                    if (requestMethod.equals("GET")) {
                        dataList = JsonUtil.getDataList(param.getUrl(), param.getCls());
                    } else {
                        dataList = JsonUtil.postDataList(param.getUrl(), param.getCls(), requestParam);
                    }
                    final List<T> resultList = dataList;
                    responseHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.callback(resultList);
                        }
                    });
                }
                super.handleMessage(msg);
            }
        };
    }

    public void sendMessage(TaskParam<T> param) {
        handler.obtainMessage(GlobalConst.CALL_BACKGROUND_SERVICE, param).sendToTarget();
    }

    public Handler getResponseHandler() {
        return responseHandler;
    }

    public void setResponseHandler(Handler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, Object> getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(Map<String, Object> requestParam) {
        this.requestParam = requestParam;
    }

    public JsonCallback<T> getCallback() {
        return callback;
    }

    public void setCallback(JsonCallback<T> callback) {
        this.callback = callback;
    }
}
