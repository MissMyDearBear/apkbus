package com.apkbus.weather.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.view.SurfaceHolder
import com.apkbus.weather.R
import com.apkbus.weather.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * description:
 * author: bear .
 * Created date:  2017/10/18.
 */
class SplashActivity : BaseActivity(), SurfaceHolder.Callback {

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        try {
            media = MediaPlayer.create(mActivity, R.raw.loading)
            media?.setDisplay(p0)
            media?.start()
            media?.setOnCompletionListener { btn.performClick() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private var media: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)
        btn.setOnClickListener({
            MainActivity.action(mActivity, "苏州", "江苏")
            finish()
        })

    }


    override fun onDestroy() {
        super.onDestroy()
        media?.release()
        media = null
    }

}