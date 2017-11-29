package com.apkbus.weather.sharedPreference

class WeatherSpKey {
    companion object {
        /**
         * 获取天气后的json字符串
         * 【value String】
         */
        var data: String? = null

        /**
         * 当前选择的省份
         * 【value String】
         */
        var provinceName = "province"
        /**
         * 当前选择的城市
         * 【value String】
         */
        var cityName = "city"
        /**
         * 当前选择的城市
         * 【value String】
         */
        var townName = "town"
    }
}