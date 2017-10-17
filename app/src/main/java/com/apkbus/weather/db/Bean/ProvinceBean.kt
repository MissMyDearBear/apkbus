package com.apkbus.weather.db.Bean

import com.apkbus.weather.utils.CityUtils.LEVEL_PROVINCE
import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Generated
import org.greenrobot.greendao.annotation.Id
import org.greenrobot.greendao.annotation.Index

/**
 * description:
 * author: bear .
 * Created date:  2017/10/10.
 */
@Entity(indexes = arrayOf(Index(value = "provinceName")))
class ProvinceBean {
    @Id(autoincrement = true)
    var id: Long = 0
    var provinceName: String? = null
    var level = LEVEL_PROVINCE

    @Generated(hash = 1999810692)
    constructor(id: Long, provinceName: String, level: Int) {
        this.id = id
        this.provinceName = provinceName
        this.level = level
    }

    @Generated(hash = 1410713511)
    constructor()
}
