package com.apkbus.weather.api;

/**
 * description:
 * author: bear .
 * Created date:  2017/10/10.
 */
public interface ApiCallBack {
    void onSuccess(String result);
    void onError(String msg);
}
