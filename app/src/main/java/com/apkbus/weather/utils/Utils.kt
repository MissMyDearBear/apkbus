package com.apkbus.weather.utils

import android.widget.Toast
import com.apkbus.weather.base.BaseActivity

fun showToast(context: BaseActivity?, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
