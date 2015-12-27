package com.xiaofo1022.xueduroid.core;

import android.util.Log;

import com.xiaofo1022.xueduroid.model.FansAnswer;
import com.xiaofo1022.xueduroid.model.SupplementAnswer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiaofo on 2015/12/24.
 */
public class JsonUtil {

    public static <T> List<T> getDataList(String url, Class<T> cls) {
        List<T> dataList = null;
        try {
            String jsonString = NetUtil.doGetJsonString(url);
            dataList = createDataListByJsonString(jsonString, cls);
        } catch (Exception e) {
            Log.e("JsonUtil", "getDataList", e);
        }
        return dataList;
    }

    public static <T> List<T> postDataList(String url, Class<T> cls, Map<String, Object> paramData) {
        List<T> dataList = null;
        try {
            String jsonString = new String(NetUtil.doPost(url, paramData));
            dataList = createDataListByJsonString(jsonString, cls);
        } catch (Exception e) {
            Log.e("JsonUtil", "getDataList", e);
        }
        return dataList;
    }

    private static <T> List<T> createDataListByJsonString(String jsonString, Class<T> cls) {
        List<T> dataList = null;
        try {
            Object value = new JSONTokener(jsonString).nextValue();
            JSONArray jsonArray = null;
            if (value instanceof JSONObject) {
                jsonArray = new JSONArray();
                jsonArray.put(value);
            } else if (value instanceof  JSONArray) {
                jsonArray = (JSONArray)value;
            }
            if (jsonArray != null && jsonArray.length() > 0) {
                dataList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    dataList.add(createDataByJson(jsonArray.getJSONObject(i), cls));
                }
            }
        } catch (Exception e) {
            Log.e("JsonUtil", "createDataListByJsonString", e);
        }
        return dataList;
    }

    private static <T> T createDataByJson(JSONObject jsonObject, Class<T> cls) {
        T data = null;
        try {
            data = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType() == int.class) {
                    int value = jsonObject.getInt(field.getName());
                    field.set(data, value);
                } else if (field.getType() == String.class) {
                    try {
                        field.set(data, jsonObject.getString(field.getName()));
                    } catch (Exception e) {
                        field.set(data, "");
                    }
                } else if (field.getType() == Date.class) {
                    try {
                        long value = jsonObject.getLong(field.getName());
                        field.set(data, new Date(value));
                    } catch (Exception e) {
                        field.set(data, new Date());
                    }
                } else if (field.getType() == FansAnswer.class) {
                    JSONObject jObject = jsonObject.optJSONObject("fansAnswer");
                    if (jObject != null && jObject.length() > 0) {
                        FansAnswer fansAnswer = createDataByJson(jObject, FansAnswer.class);
                        field.set(data, fansAnswer);
                    }
                } else {
                    if (field.getName().equals("supplementAnswerList")) {
                        JSONArray jsonArray = jsonObject.optJSONArray("supplementAnswerList");
                        if (jsonArray != null && jsonArray.length() > 0) {
                            List<SupplementAnswer> supplementAnswerList = new ArrayList<>(jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                supplementAnswerList.add(createDataByJson(jsonArray.getJSONObject(i), SupplementAnswer.class));
                            }
                            field.set(data, supplementAnswerList);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("JsonUtil", "createDataByJson", e);
        }
        return data;
    }
}
