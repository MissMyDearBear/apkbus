package com.apkbus.weather.activity

import android.content.Intent
import android.os.Bundle
import com.apkbus.weather.R
import com.apkbus.weather.base.BaseActivity
import com.apkbus.weather.fragment.CityWeatherFragment
import com.apkbus.weather.utils.CityUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    var city:String? = ""
    var province: String? = ""

    companion object {
        fun action(activity: BaseActivity?, city: String, province: String) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("city", city)
            intent.putExtra("province", province)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CityUtils.getAllCities(this)
        root.setBackgroundResource(R.drawable.ic_background1)

        if (intent != null) {
            city = intent.getStringExtra("city")
            province = intent.getStringExtra("province")
        }

        val fragment = CityWeatherFragment()
        val bundle = Bundle()
        bundle.putString("city", city)
        bundle.putString("province", province)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
        supportFragmentManager.beginTransaction().show(fragment).commit()
    }
}
