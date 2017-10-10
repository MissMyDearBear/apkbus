package com.apkbus.weather.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import static com.apkbus.weather.utils.CityUtils.LEVEL_DISTRICT;

/**
 * description:
 * author: bear .
 * Created date:  2017/10/10.
 */
@Entity(indexes = {@Index(value = "cityName"), @Index(value = "districtName")})
public class DistrictBean {
    @Id(autoincrement = true)
    public long id;
    public String cityName;
    public String districtName;
    public int level = LEVEL_DISTRICT;

    @Generated(hash = 903270775)
    public DistrictBean(long id, String cityName, String districtName, int level) {
        this.id = id;
        this.cityName = cityName;
        this.districtName = districtName;
        this.level = level;
    }

    @Generated(hash = 326445391)
    public DistrictBean() {
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

    public String getDistrictName() {
        return this.districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
