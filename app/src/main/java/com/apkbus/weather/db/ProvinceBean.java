package com.apkbus.weather.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import static com.apkbus.weather.utils.CityUtils.LEVEL_PROVINCE;

/**
 * description:
 * author: bear .
 * Created date:  2017/10/10.
 */
@Entity(indexes = {@Index(value = "provinceName")})
public class ProvinceBean {
    @Id(autoincrement = true)
    public long id;
    public String provinceName;
    public int level = LEVEL_PROVINCE;

    @Generated(hash = 1999810692)
    public ProvinceBean(long id, String provinceName, int level) {
        this.id = id;
        this.provinceName = provinceName;
        this.level = level;
    }

    @Generated(hash = 1410713511)
    public ProvinceBean() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
