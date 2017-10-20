package com.apkbus.weather.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apkbus.weather.R
import com.apkbus.weather.activity.ChooseLocationActivity
import com.apkbus.weather.api.ApiCallBack
import com.apkbus.weather.api.ApiHelper
import com.apkbus.weather.entry.IndexBean
import com.apkbus.weather.entry.WeatherBean
import com.apkbus.weather.sharedPreference.WeatherSpKey
import com.apkbus.weather.utils.GsonUtils
import com.apkbus.weather.utils.getWeatherDataSp
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_city_weather.*

class CityWeatherFragment : Fragment() {
    private val REQUSET_SELECT_CITY = 1101
    private var province: String? = ""
    private var city: String? = ""
    private var mActivity = this.activity

    private val TAG_API = "ApiCallBack:"
    private var rootView: View? = null
    private var currWeatherDetail = ArrayList<IndexBean>()
    private var tenDayWeatherDetail = ArrayList<WeatherBean.ResultBean.FutureBean>()
    private var weatherBean: WeatherBean? = null
    private var gridAdapter = MyGridViewAdapter(R.layout.item_weather_index, currWeatherDetail)
    private var recyclerAdapter = MyRecyclerViewAdapter(R.layout.item_weather_prediction, tenDayWeatherDetail)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        city = arguments.getString("city")
        province = arguments.getString("province")

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater?.inflate(R.layout.fragment_city_weather, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        address.setOnClickListener({
            val intent = Intent(this.activity, ChooseLocationActivity::class.java)
            this.activity.startActivityForResult(intent, REQUSET_SELECT_CITY)
        })
        if (TextUtils.isEmpty(province) && TextUtils.isEmpty(city)) {
            province = "北京"
            city = "北京"
        }
        onRefresh()
        grid?.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false)
        grid?.adapter = gridAdapter
        recycler?.layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false)
        recycler?.adapter = recyclerAdapter

    }

    fun onRefresh() {
        var json = getWeatherDataSp().getString(WeatherSpKey.data, "")
        if (!TextUtils.isEmpty(json)) {
            dealWeatherJson(json, province, city)
        } else {
            getWeatherDetail(province, city)
        }
    }

    private fun getWeatherDetail(province: String?, city: String?) {
        ApiHelper.getWeatherDetail(this.activity, province, city, object : ApiCallBack {
            override fun onSuccess(result: String) {
                getWeatherDataSp().edit().putString(WeatherSpKey.data, result).apply()
                dealWeatherJson(result, province, city)
            }

            override fun onError(msg: String) {
                Log.e(TAG_API, msg)
            }
        })
    }

    private fun dealWeatherJson(result: String, province: String?, city: String?) {
        if (!TextUtils.isEmpty(result)) {
            weatherBean = GsonUtils.jsonToClass(result, WeatherBean::class.java)
            tenDayWeatherDetail.clear()
            tenDayWeatherDetail.addAll(weatherBean?.result?.get(0)?.future!!)
            recyclerAdapter.notifyDataSetChanged()
            toText(address, province + "——" + city)
            toText(big_temperature, weatherBean?.result?.get(0)?.temperature)
            toText(weather, "天气情况：" + weatherBean?.result?.get(0)?.weather)
            toText(airCondition, "空气质量：" + weatherBean?.result?.get(0)?.airCondition)
            currWeatherDetail.clear()
            currWeatherDetail.add(0, IndexBean("风向风力", weatherBean?.result?.get(0)?.wind))
            currWeatherDetail.add(1, IndexBean("日出时间", weatherBean?.result?.get(0)?.sunrise))
            currWeatherDetail.add(2, IndexBean("日落时间", weatherBean?.result?.get(0)?.sunset))
            currWeatherDetail.add(3, IndexBean("锻炼指数", weatherBean?.result?.get(0)?.exerciseIndex))
            currWeatherDetail.add(4, IndexBean("穿衣指数", weatherBean?.result?.get(0)?.dressingIndex))
            currWeatherDetail.add(5, IndexBean("洗衣指数", weatherBean?.result?.get(0)?.washIndex))
            gridAdapter?.notifyDataSetChanged()
        }
    }

    class MyGridViewAdapter(layoutRes: Int, datas: List<IndexBean>?) :
            BaseQuickAdapter<IndexBean, BaseViewHolder>(layoutRes, datas) {
        override fun convert(viewHolder: BaseViewHolder?, item: IndexBean) {
            toText(viewHolder!!.getView<TextView>(R.id.item_key), item.indexName)
            toText(viewHolder.getView<TextView>(R.id.item_value), item.indexContent)
        }
    }

    class MyRecyclerViewAdapter(layoutRes: Int, datas: List<WeatherBean.ResultBean.FutureBean>?) :
            BaseQuickAdapter<WeatherBean.ResultBean.FutureBean, BaseViewHolder>(layoutRes, datas) {
        override fun convert(viewHolder: BaseViewHolder?, item: WeatherBean.ResultBean.FutureBean) {
            toText(viewHolder!!.getView<TextView>(R.id.date), item.date)
            toText(viewHolder.getView<TextView>(R.id.dayTime), item.dayTime)
            toText(viewHolder.getView<TextView>(R.id.night), item.night)
            toText(viewHolder.getView<TextView>(R.id.temperature_section), item.temperature)
            toText(viewHolder.getView<TextView>(R.id.wind), item.wind)
            toText(viewHolder.getView<TextView>(R.id.week), item.week)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUSET_SELECT_CITY && resultCode == Activity.RESULT_OK && data != null) {
            val currCityName = data.getStringExtra("cityName")
            val currProvinceName = data.getStringExtra("provinceName")

            getWeatherDataSp().edit().putString("provinceName", currProvinceName)
                    .putString("cityName", currCityName).apply()
            getWeatherDetail(currProvinceName, currCityName)
        }
    }

    companion object {
        // TextView非空赋值，空划线
        fun toText(text: TextView, str: String?) {
            if (TextUtils.isEmpty(str)) {
                text.text = "----"
            } else {
                text.text = str
            }
        }
    }

}