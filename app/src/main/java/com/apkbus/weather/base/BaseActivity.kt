package com.apkbus.weather.base

import android.os.Bundle
import com.apkbus.weather.library.app.SwipeBackActivity

abstract class BaseActivity : SwipeBackActivity() {
    lateinit var mActivity: BaseActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
    }
}