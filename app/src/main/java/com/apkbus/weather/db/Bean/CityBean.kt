package com.apkbus.weather.db.Bean

import com.apkbus.weather.utils.CityUtils.LEVEL_CITY
import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Generated
import org.greenrobot.greendao.annotation.Id
import org.greenrobot.greendao.annotation.Index

@Entity(indexes = arrayOf(Index(value = "cityName"), Index(value = "provinceName")))
class CityBean {
    @Id(autoincrement = true)
    var id: Long = 0
    var cityName: String? = null
    var provinceName: String? = null
    var level = LEVEL_CITY

    @Generated(hash = 83209856)
    constructor(id: Long, cityName: String, provinceName: String, level: Int) {
        this.id = id
        this.cityName = cityName
        this.provinceName = provinceName
        this.level = level
    }

    @Generated(hash = 273649691)
    constructor()
}
