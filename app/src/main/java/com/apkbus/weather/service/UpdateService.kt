package com.apkbus.weather.service

import android.app.Activity
import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.apkbus.weather.api.ApiCallBack
import com.apkbus.weather.api.ApiHelper
import com.apkbus.weather.sharedPreference.WeatherSpKey
import com.apkbus.weather.utils.getWeatherDataSp

/**
 * description:
 * author: bear .
 * Created date:  2017/10/20.
 */
class UpdateService : IntentService("updateWeather") {

    override fun onHandleIntent(p0: Intent?) {
        val sp = getWeatherDataSp()
        var pName: String = sp.getString(WeatherSpKey.provinceName, "北京")
        var cName: String = sp.getString(WeatherSpKey.cityName, "北京")

        ApiHelper.getWeatherDetail(null, pName, cName, object : ApiCallBack {
            override fun onSuccess(result: String) {
                getWeatherDataSp().edit().putString(WeatherSpKey.data, result).apply()
            }

            override fun onError(msg: String) {
            }

        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var manager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val refreshTime = 8 * 60 * 60 * 1000

        var triggerAtTime = SystemClock.elapsedRealtime() + refreshTime
        val intent = Intent(this, UpdateService::class.java)
        var pi = PendingIntent.getService(this, 0, intent, 0)
        manager.cancel(pi)
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi)
        println("调用时间：" + System.currentTimeMillis().toString())
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        fun startUpdateService(activity: Activity) {
            var intent = Intent(activity, UpdateService::class.java)
            activity.startService(intent)
        }
    }

}