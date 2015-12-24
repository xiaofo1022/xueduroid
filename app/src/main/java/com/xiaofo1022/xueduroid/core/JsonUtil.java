package com.xiaofo1022.xueduroid.core;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Xiaofo on 2015/12/24.
 */
public class JsonUtil {

    public static <T> List<T> getDataList(String url, Class<T> cls) {
        List<T> dataList = null;
        String jsonString = NetUtil.doGetJsonString(url);
        try {
            JSONArray jsonArray = (JSONArray)new JSONTokener(jsonString).nextValue();
            if (jsonArray != null && jsonArray.length() > 0) {
                dataList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    dataList.add(createDataByJson(jsonArray.getJSONObject(i), cls));
                }
            }
        } catch (JSONException e) {
            Log.e("JsonUtil", "getDataList", e);
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
                    String value = jsonObject.getString(field.getName());
                    field.set(data, value);
                } else if (field.getType() == Date.class) {
                    long value = jsonObject.getLong(field.getName());
                    field.set(data, new Date(value));
                }
            }
        } catch (Exception e) {
        }
        return data;
    }
}
