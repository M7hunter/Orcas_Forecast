package com.m7.orcasforecast.data.model

import androidx.room.*

@Entity
data class MainRes(
    @PrimaryKey
    var cityId: Int?,
    var cityName: String?,
    var cod: String?,
    var message: String?,
    var cnt: Int?,
    @Embedded
    var city: City?,
    @Ignore
    val list: List<Forecast>?
) {
    constructor(
        cityId: Int?,
        cityName: String?,
        cod: String?,
        message: String?,
        cnt: Int?,
        city: City?
    ) : this(cityId, cityName, cod, message, cnt, city, null)
}
