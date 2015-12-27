package com.xiaofo1022.xueduroid.core;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Xiaofo on 2015/12/24.
 */
public class NetUtil {
    public static byte[] doGet(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return getInputBytes(connection.getInputStream());
            }
        } catch (Exception e) {
            Log.e("NetUtil", "doGet", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private static byte[] getInputBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int readBytes = 0;
        while ((readBytes = in.read(buffer)) > 0) {
            out.write(buffer, 0, readBytes);
        }
        out.close();
        in.close();
        return out.toByteArray();
    }

    public static byte[] doPost(String urlAddress, Map<String, Object> paramData) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            byte[] data = encodeParamData(paramData);
            out.write(data, 0, data.length);
            out.flush();
            out.close();

            return getInputBytes(connection.getInputStream());
        } catch (Exception e) {
            Log.e("NetUtil", "doPost", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private static byte[] encodeParamData(Map<String, Object> paramData) throws UnsupportedEncodingException, JSONException {
        JSONObject jsonObject = new JSONObject();
        for (String key : paramData.keySet()) {
            jsonObject.put(key, paramData.get(key));
        }
        return jsonObject.toString().getBytes("utf-8");
    }

    public static String doGetJsonString(String urlAddress) {
        try {
            byte[] datas = doGet(urlAddress);
            if (datas != null && datas.length > 0) {
                return new String(datas);
            } else {
                return "";
            }
        } catch (Exception e) {
            Log.e("NetUtil", "doGetJsonString", e);
            return "";
        }
    }
}
