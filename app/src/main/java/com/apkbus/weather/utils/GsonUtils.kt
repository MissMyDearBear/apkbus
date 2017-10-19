package com.apkbus.weather.utils

import android.text.TextUtils
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * description:
 * author: bear .
 * Created date:  2017/10/10.
 */
object GsonUtils {
    /**
     * 将json字符串转化为一个对象
     *
     * @param json     ：json字符串
     * @param classOfT ：对象的Class
     * @param <T>      要转化的对象
     * @return null 或者 一个T类型的对象
    </T> */
    fun <T> jsonToClass(json: String, classOfT: Class<T>): T? {
        var t: T? = null
        try {
            t = Gson().fromJson(json, classOfT)
        } catch (e: Exception) {
            println("json to class【" + classOfT + "】 解析失败  " + e.message)
        }
        return t
    }

    /**
     * 将json字符串转化为一个对象列表
     *
     * @param json    ：json字符串
     * @param typeOfT ：对象列表的 type
     * @param <T>     要转化的对象
     * @return null 或者 一个对象列表
    </T> */
    fun <T> jsonToList(json: String, typeOfT: Type): List<T>? {
        var items: List<T>? = null
        if (!TextUtils.isEmpty(json)) {
            try {
                items = Gson().fromJson<List<T>>(json, typeOfT)
            } catch (e: Exception) {
                println("json to list 解析失败  " + e.message)
            }

        }
        return items
    }

    fun <T> jsonToList(json: String): List<T>? {
        var items: List<T>? = null
        if (!TextUtils.isEmpty(json)) {
            try {
                items = Gson().fromJson<List<T>>(json, object : TypeToken<List<T>>() {

                }.type)
            } catch (e: Exception) {
                println("json to list 解析失败  " + e.message)
            }
        }
        return items
    }


    /**
     * 将一个对象转化成json字符串
     *
     * @param object
     * @return
     */
    fun toJson(`object`: Any): String {
        var jsonStr = ""
        try {
            jsonStr = Gson().toJson(`object`)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return jsonStr
    }
}
