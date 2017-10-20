package com.apkbus.weather.sharedPreference

/**
 * description:
 * author: bear .
 * Created date:  2017/10/20.
 */
class WeatherSpKey{
    companion object {
        /**
         * 获取天气后的json字符串
         * 【value String】
         */
         var data:String?=null

        /**
         * 当前选择的省份
         * 【value String】
         */
        var  provinceName="provinceName"
        /**
         * 当前选择的城市
         * 【value String】
         */
        var  cityName="cityName"
    }

}