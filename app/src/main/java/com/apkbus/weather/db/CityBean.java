package com.apkbus.weather.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import static com.apkbus.weather.utils.CityUtils.LEVEL_CITY;

/**
 * description:
 * author: bear .
 * Created date:  2017/10/10.
 */
@Entity(indexes = {@Index(value = "cityName"), @Index(value = "provinceName")})
public class CityBean {
    @Id(autoincrement = true)
    public long id;
    public String cityName;
    public String provinceName;
    public int level = LEVEL_CITY;

    @Generated(hash = 83209856)
    public CityBean(long id, String cityName, String provinceName, int level) {
        this.id = id;
        this.cityName = cityName;
        this.provinceName = provinceName;
        this.level = level;
    }

    @Generated(hash = 273649691)
    public CityBean() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
