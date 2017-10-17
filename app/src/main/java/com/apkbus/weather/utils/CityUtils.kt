package com.apkbus.weather.utils

import android.app.Activity
import com.apkbus.weather.api.ApiCallBack
import com.apkbus.weather.api.ApiHelper
import com.apkbus.weather.base.MyApplication
import com.apkbus.weather.db.Bean.CityBean
import com.apkbus.weather.db.Bean.DistrictBean
import com.apkbus.weather.db.Bean.ProvinceBean
import com.apkbus.weather.db.Dao.CityBeanDao
import com.apkbus.weather.db.Dao.DistrictBeanDao
import com.apkbus.weather.entry.CityResult
import java.util.*
import kotlin.collections.ArrayList

/**
 * description:
 * author: bear .
 * Created date:  2017/10/10.
 */
object CityUtils {
    val LEVEL_PROVINCE = 0//省 直辖市 自治区
    val LEVEL_CITY = 1//市
    val LEVEL_DISTRICT = 2//区县
    fun getAllCities(activity: Activity) {
        if (MyApplication.INSTANCE.daoSession!!.provinceBeanDao.queryBuilder().count() > 0) {
            return
        }
        val params = HashMap<String, String>()
        params.put("key", ApiHelper.KEY)
        ApiHelper.get(activity, "/weather/citys", params, object : ApiCallBack {
            override fun onSuccess(result: String) {
                val bean = GsonUtils.jsonToClass(result, CityResult::class.java)
                var provinceId=0
                var cityId=0
                var districtId=0
                val pList=ArrayList<ProvinceBean>()
                val cList=ArrayList<CityBean>()
                val dList=ArrayList<DistrictBean>()
                if (bean?.result != null && bean.result!!.isNotEmpty()) {
                    for (resultBean in bean.result!!) {
                        val province = ProvinceBean()
                        province.provinceName = resultBean.province
                        province.id= provinceId.toLong()
                        pList.add(province)
                        provinceId++
                        if (resultBean.city != null && resultBean.city!!.isNotEmpty()) {
                            for (cityBean in resultBean.city!!) {
                                val city = CityBean()
                                city.cityName = cityBean.city
                                city.provinceName = resultBean.province
                                city.id=cityId.toLong()
                                cList.add(city)

                                cityId++
                                if (cityBean.district != null && cityBean.district!!.isNotEmpty()) {
                                    for (districtBean in cityBean.district!!) {
                                        val district = DistrictBean()
                                        district.cityName = cityBean.city
                                        district.districtName = districtBean.district
                                        district.id=districtId.toLong()
                                        dList.add(district)
                                        districtId++
                                    }
                                }
                            }
                        }
                    }
                    MyApplication.INSTANCE.daoSession?.provinceBeanDao?.saveInTx(pList)
                    MyApplication.INSTANCE.daoSession?.cityBeanDao?.saveInTx(cList)
                    MyApplication.INSTANCE.daoSession?.districtBeanDao?.saveInTx(dList)
                }
            }

            override fun onError(msg: String) {}
        })
    }

    fun <T> getListData(key: String, level: Int): ArrayList<T> {
        val list = ArrayList<T>()
        when (level) {
            LEVEL_PROVINCE -> {
                val province = MyApplication.INSTANCE.daoSession?.provinceBeanDao?.loadAll()
                list.addAll(province as Collection<T>)
            }
            LEVEL_CITY -> {
                val city = MyApplication.INSTANCE.daoSession?.cityBeanDao
                        ?.queryBuilder()?.where(CityBeanDao.Properties.ProvinceName.eq(key))?.build()?.list()
                list.addAll(city as Collection<T>)
            }
            LEVEL_DISTRICT -> {
                val district = MyApplication.INSTANCE.daoSession?.districtBeanDao
                        ?.queryBuilder()?.where(DistrictBeanDao.Properties.CityName.eq(key))?.build()?.list()
                list.addAll(district as Collection<T>)
            }
        }
        return list
    }
}
