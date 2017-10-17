package com.apkbus.weather.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import org.greenrobot.greendao.database.Database

/**
 * description: GreenDao帮助类
 * author: bear .
 * Created date:  2017/5/17.
 */
class MyOpenHelper : DaoMaster.DevOpenHelper {
    constructor(context: Context, name: String) : super(context, name)

    constructor(context: Context, name: String, factory: SQLiteDatabase.CursorFactory) : super(context, name, factory)

    override fun onUpgrade(db: Database?, oldVersion: Int, newVersion: Int) {
        /*此处不用super，因为父类中包含了
       dropAllTables(db, true);
        onCreate(db);
        需要自己定制升级
        */
        //       MigrationHelper.getInstance().dropAndCreate(db);
    }
}
