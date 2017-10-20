package com.apkbus.weather.utils

import android.widget.Toast
import com.apkbus.weather.base.BaseActivity

/**
 * description:
 * author: bear .
 * Created date:  2017/10/19.
 */
fun showToast(context: BaseActivity?, msg:String){
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
}
