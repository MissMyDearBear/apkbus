package com.apkbus.weather.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * description:
 * author: bear .
 * Created date:  2017/10/10.
 */
public class GsonUtils {
    /**
     * 将json字符串转化为一个对象
     *
     * @param json     ：json字符串
     * @param classOfT ：对象的Class
     * @param <T>      要转化的对象
     * @return null 或者 一个T类型的对象
     */
    public static <T> T jsonToClass(String json, Class<T> classOfT) {
        T t = null;
        try {
            t = new Gson().fromJson(json, classOfT);
        } catch (Exception e) {
            System.out.println("json to class【" + classOfT + "】 解析失败  " + e.getMessage());
        }
        return t;
    }

    /**
     * 将json字符串转化为一个对象列表
     *
     * @param json    ：json字符串
     * @param typeOfT ：对象列表的 type
     * @param <T>     要转化的对象
     * @return null 或者 一个对象列表
     */
    public static <T> List<T> jsonToList(String json, Type typeOfT) {
        List<T> items = null;
        if (!TextUtils.isEmpty(json)) {
            try {
                items = new Gson().fromJson(json, typeOfT);
            } catch (Exception e) {
                System.out.println("json to list 解析失败  " + e.getMessage());
            }
        }
        return items;
    }


    public static <T> List<T> jsonToList(String json) {
        List<T> items = null;
        if (!TextUtils.isEmpty(json)) {
            try {
                items = new Gson().fromJson(json, new TypeToken<List<T>>() {
                }.getType());
            } catch (Exception e) {
                System.out.println("json to list 解析失败  " + e.getMessage());
            }
        }
        return items;
    }


    /**
     * 将一个对象转化成json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        String jsonStr = "";
        try {
            jsonStr = new Gson().toJson(object);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonStr;
    }
}
