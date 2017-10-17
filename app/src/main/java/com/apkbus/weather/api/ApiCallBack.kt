package com.apkbus.weather.api

interface ApiCallBack {
    fun onSuccess(result: String)
    fun onError(msg: String)
}
