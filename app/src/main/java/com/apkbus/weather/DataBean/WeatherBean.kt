package com.apkbus.weather.DataBean

class WeatherBean {
    /**
     * msg : success
     * result : [{"airCondition":"优","city":"南京","coldIndex":"易发期","date":"2017-10-12","distrct":"南京","dressingIndex":"夹衣类","exerciseIndex":"不适宜","future":[{"date":"2017-10-12","dayTime":"小雨-中雨","night":"阴","temperature":"16°C / 13°C","week":"今天","wind":"东北风 4～5级"},{"date":"2017-10-13","dayTime":"阴","night":"阵雨","temperature":"20°C / 15°C","week":"星期五","wind":"东北风 3～4级"},{"date":"2017-10-14","dayTime":"阵雨","night":"阵雨","temperature":"19°C / 16°C","week":"星期六","wind":"东北风 3～4级"},{"date":"2017-10-15","dayTime":"阵雨","night":"阵雨","temperature":"19°C / 15°C","week":"星期日","wind":"东北风 3～4级"},{"date":"2017-10-16","dayTime":"阵雨","night":"阵雨","temperature":"19°C / 15°C","week":"星期一","wind":"东北风 3～4级"},{"date":"2017-10-17","dayTime":"阴","night":"阴","temperature":"20°C / 15°C","week":"星期二","wind":"东北风 4～5级"},{"date":"2017-10-18","dayTime":"雨","night":"多云","temperature":"18°C / 13°C","week":"星期三","wind":"东北偏北风 2级"},{"date":"2017-10-19","dayTime":"局部多云","night":"局部多云","temperature":"22°C / 12°C","week":"星期四","wind":"北风 2级"},{"date":"2017-10-20","dayTime":"晴","night":"晴","temperature":"22°C / 12°C","week":"星期五","wind":"东北风 3级"},{"date":"2017-10-21","dayTime":"少云","night":"少云","temperature":"22°C / 13°C","week":"星期六","wind":"东北偏东风 3级"}],"humidity":"湿度：88%","pollutionIndex":"21","province":"江苏","sunrise":"06:06","sunset":"17:37","temperature":"15℃","time":"09:41","updateTime":"20171012095407","washIndex":"不适宜","weather":"阴","week":"周四","wind":"北风4级"}]
     * retCode : 200
     */
    var msg: String? = null
    var retCode: String? = null
    var result: List<ResultBean>? = null

    class ResultBean {
        /**
         * airCondition : 优
         * city : 南京
         * coldIndex : 易发期
         * date : 2017-10-12
         * distrct : 南京
         * dressingIndex : 夹衣类
         * exerciseIndex : 不适宜
         * future : [{"date":"2017-10-12","dayTime":"小雨-中雨","night":"阴","temperature":"16°C / 13°C","week":"今天","wind":"东北风 4～5级"},{"date":"2017-10-13","dayTime":"阴","night":"阵雨","temperature":"20°C / 15°C","week":"星期五","wind":"东北风 3～4级"},{"date":"2017-10-14","dayTime":"阵雨","night":"阵雨","temperature":"19°C / 16°C","week":"星期六","wind":"东北风 3～4级"},{"date":"2017-10-15","dayTime":"阵雨","night":"阵雨","temperature":"19°C / 15°C","week":"星期日","wind":"东北风 3～4级"},{"date":"2017-10-16","dayTime":"阵雨","night":"阵雨","temperature":"19°C / 15°C","week":"星期一","wind":"东北风 3～4级"},{"date":"2017-10-17","dayTime":"阴","night":"阴","temperature":"20°C / 15°C","week":"星期二","wind":"东北风 4～5级"},{"date":"2017-10-18","dayTime":"雨","night":"多云","temperature":"18°C / 13°C","week":"星期三","wind":"东北偏北风 2级"},{"date":"2017-10-19","dayTime":"局部多云","night":"局部多云","temperature":"22°C / 12°C","week":"星期四","wind":"北风 2级"},{"date":"2017-10-20","dayTime":"晴","night":"晴","temperature":"22°C / 12°C","week":"星期五","wind":"东北风 3级"},{"date":"2017-10-21","dayTime":"少云","night":"少云","temperature":"22°C / 13°C","week":"星期六","wind":"东北偏东风 3级"}]
         * humidity : 湿度：88%
         * pollutionIndex : 21
         * province : 江苏
         * sunrise : 06:06
         * sunset : 17:37
         * temperature : 15℃
         * time : 09:41
         * updateTime : 20171012095407
         * washIndex : 不适宜
         * weather : 阴
         * week : 周四
         * wind : 北风4级
         */
        var airCondition: String? = null
        var city: String? = null
        var coldIndex: String? = null
        var date: String? = null
        var distrct: String? = null
        var dressingIndex: String? = null
        var exerciseIndex: String? = null
        var humidity: String? = null
        var pollutionIndex: String? = null
        var province: String? = null
        var sunrise: String? = null
        var sunset: String? = null
        var temperature: String? = null
        var time: String? = null
        var updateTime: String? = null
        var washIndex: String? = null
        var weather: String? = null
        var week: String? = null
        var wind: String? = null
        var future: List<FutureBean>? = null

        class FutureBean {
            /**
             * date : 2017-10-12
             * dayTime : 小雨-中雨
             * night : 阴
             * temperature : 16°C / 13°C
             * week : 今天
             * wind : 东北风 4～5级
             */
            var date: String? = null
            var dayTime: String? = null
            var night: String? = null
            var temperature: String? = null
            var week: String? = null
            var wind: String? = null
        }
    }
}
