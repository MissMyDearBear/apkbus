package com.apkbus.weather.base

import android.app.Application
import com.apkbus.weather.db.DaoMaster
import com.apkbus.weather.db.DaoSession
import com.apkbus.weather.db.MyOpenHelper

class MyApplication : Application() {
    var daoSession: DaoSession? = null

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        val help = MyOpenHelper(this, if (ENCRYPTED) "bear-db-encrypted" else "bear-db")
        val db = if (ENCRYPTED) help.getEncryptedWritableDb("admin") else help.writableDb
        val master = DaoMaster(db)
        print("当前数据库版本号-->" + master.schemaVersion)
        daoSession = master.newSession()
    }

    companion object {
        lateinit var INSTANCE: MyApplication
        val ENCRYPTED = true
    }
}
