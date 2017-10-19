package com.apkbus.weather.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import com.apkbus.weather.R
import com.apkbus.weather.base.BaseActivity
import com.apkbus.weather.base.MyApplication
import com.apkbus.weather.db.Bean.CityBean
import com.apkbus.weather.db.Bean.DistrictBean
import com.apkbus.weather.db.Bean.ProvinceBean
import com.apkbus.weather.db.DaoSession
import com.apkbus.weather.utils.CityUtils
import com.apkbus.weather.wheel_widget.OnWheelChangedListener
import com.apkbus.weather.wheel_widget.WheelView
import com.apkbus.weather.wheel_widget.adapters.ArrayWheelAdapter
import kotlinx.android.synthetic.main.activity_choose_location.*

class ChooseLocationActivity : BaseActivity(), OnWheelChangedListener {
    var daoSession: DaoSession? = null

    // 省列表
    var mProvinceDatas = ArrayList<String>()
    // 市列表
    var mCitisDatas = ArrayList<String>()
    // 县区列表
    var mDistrictDatas = ArrayList<String>()

    // 当前省的名称
    var mCurrentProviceName = ""
    // 当前市的名称
    var mCurrentCityName = ""
    // 当前区的名称
    var mCurrentDistrictName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_location)
        root.setBackgroundResource(R.drawable.ic_background2)

        initWheelViews()
        setOnWheelChangeListener()

        confirmButton.setOnClickListener({

            var intent:Intent=Intent();
            intent.putExtra("cityName",mCurrentCityName)
            intent.putExtra("provinceName",mCurrentProviceName)
            setResult(Activity.RESULT_OK,intent)
            finish()
        })
    }

    fun initWheelViews() {
        daoSession = MyApplication.INSTANCE.daoSession
        // 给 “省”转轮View 赋值，传*空值*直接遍历全部“省”信息，并添加至省字符串列表
        for (i in CityUtils.getListData<ProvinceBean>("", CityUtils.LEVEL_PROVINCE)) {
            mProvinceDatas.add(i.provinceName!!)
        }
        provinceWheelView.viewAdapter = ArrayWheelAdapter<String>(this, mProvinceDatas)
        mCurrentProviceName = mProvinceDatas[0]
        for (i in CityUtils.getListData<CityBean>(mCurrentProviceName, CityUtils.LEVEL_CITY)) {
            mCitisDatas.add(i.cityName!!)
        }
        cityWheelView.viewAdapter = ArrayWheelAdapter<String>(this, mCitisDatas)
        mCurrentCityName = mCitisDatas[0]
        for (i in CityUtils.getListData<DistrictBean>(mCurrentCityName, CityUtils.LEVEL_DISTRICT)) {
            mDistrictDatas.add(i.districtName!!)
        }
        districtWheelView.viewAdapter = ArrayWheelAdapter<String>(this, mDistrictDatas)
        mCurrentDistrictName = mDistrictDatas[0]
    }

    // 更新“市”转轮View的方法
    fun updateCityWheel() {
        mCurrentProviceName = mProvinceDatas[provinceWheelView.currentItem]
        mCitisDatas.clear()
        // 给“市”转轮View 赋值，根据（“省”转轮View选中值）遍历该“省”下的所有“市”信息，并添加至市字符串列表
        for (i in CityUtils.getListData<CityBean>(mCurrentProviceName, CityUtils.LEVEL_CITY)) {
            mCitisDatas.add(i.cityName!!)
        }
        cityWheelView.viewAdapter = ArrayWheelAdapter<String>(this, mCitisDatas)
        cityWheelView.currentItem = 0
        updateDistrictWheel()
    }

    // 更新“县区”转轮View的方法
    fun updateDistrictWheel() {
        mCurrentCityName = mCitisDatas[cityWheelView.currentItem]
        mDistrictDatas.clear()
        // 给“县区”转轮View 赋值，根据（“市”转轮View选中值）遍历该“市”下的所有“县区”信息，并添加至县区字符串列表
        for (i in CityUtils.getListData<DistrictBean>(mCurrentCityName, CityUtils.LEVEL_DISTRICT)) {
            mDistrictDatas.add(i.districtName!!)
        }
        districtWheelView.viewAdapter = ArrayWheelAdapter<String>(this, mDistrictDatas)
        districtWheelView.currentItem = 0
    }

    // 给转轮们添加监听器
    private fun setOnWheelChangeListener() {
        // “省”转轮View添加 转动监听器
        provinceWheelView.addChangingListener(this)
        // “市”转轮View添加 转动监听器
        cityWheelView.addChangingListener(this)
        // “县区”转轮View添加 转动监听器
        districtWheelView.addChangingListener(this)
    }

    /* 重载所实现的监视器接口的监控控件转动的方法 */
    override fun onChanged(wheel: WheelView?, oldValue: Int, newValue: Int) {
        // 控件选择及响应时间控制
        when (wheel) {
            provinceWheelView -> updateCityWheel()
            cityWheelView -> updateDistrictWheel()
            districtWheelView -> mCurrentDistrictName = mDistrictDatas[districtWheelView.currentItem]
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> this.finish()
        }
        return super.onKeyDown(keyCode, event)
    }
}
