package com.apkbus.weather.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * description:
 * author: bear .
 * Created date:  2017/10/18.
 */
abstract class BaseActivity:AppCompatActivity(){
     var mActivity:BaseActivity?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity=this
    }

}