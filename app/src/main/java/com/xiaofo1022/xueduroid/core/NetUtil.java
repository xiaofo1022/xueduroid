package com.xiaofo1022.xueduroid.core;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
                InputStream in = connection.getInputStream();
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
        } catch (Exception e) {
            Log.e("NetUtil", "getUrlBytes", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
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
