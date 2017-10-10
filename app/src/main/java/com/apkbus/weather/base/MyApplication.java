package com.apkbus.weather.base;

import android.app.Application;

import com.apkbus.weather.db.DaoMaster;
import com.apkbus.weather.db.DaoSession;
import com.apkbus.weather.db.MyOpenHelper;

import org.greenrobot.greendao.database.Database;



/**
 * Created by bear on 2017/4/17.
 */

public class MyApplication extends Application {

    public static MyApplication INSTANCE;
    public static final boolean ENCRYPTED = true;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        DaoMaster.DevOpenHelper help = new MyOpenHelper(this, ENCRYPTED ? "bear-db-encrypted" : "bear-db");
        Database db = ENCRYPTED ? help.getEncryptedWritableDb("admin") : help.getWritableDb();
        DaoMaster master=new DaoMaster(db);
        System.out.print("当前数据库版本号-->"+master.getSchemaVersion());
        mDaoSession = master.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
