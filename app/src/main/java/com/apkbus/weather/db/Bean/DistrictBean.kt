package com.apkbus.weather.db.Bean

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Generated
import org.greenrobot.greendao.annotation.Id
import org.greenrobot.greendao.annotation.Index

import com.apkbus.weather.utils.CityUtils.LEVEL_DISTRICT

/**
 * description:
 * author: bear .
 * Created date:  2017/10/10.
 */
@Entity(indexes = arrayOf(Index(value = "cityName"), Index(value = "districtName")))
class DistrictBean {
    @Id(autoincrement = true)
    var id: Long = 0
    var cityName: String? = ""
    var districtName: String? = ""
    var level = LEVEL_DISTRICT

    @Generated(hash = 903270775)
    constructor(id: Long, cityName: String, districtName: String, level: Int) {
        this.id = id
        this.cityName = cityName
        this.districtName = districtName
        this.level = level
    }

    @Generated(hash = 326445391)
    constructor()
}
