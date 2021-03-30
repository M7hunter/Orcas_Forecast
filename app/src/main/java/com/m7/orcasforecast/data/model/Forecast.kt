package com.m7.orcasforecast.data.model

import androidx.room.*

@Entity
data class Forecast(
    var cityId: Int?,
    var cityName: String?,
    @PrimaryKey
    var dt: Long?,
    var sunrise: Long?,
    var sunset: Long?,
    var pressure: Long?,
    var humidity: Long?,
    var speed: Float?,
    var deg: Float?,
    var clouds: Float?,
    var pop: Float?,
    @Embedded
    var temp: Temp?,
    @Embedded
    var feels_like: FeelsLike?,
    @Ignore
    val weather: List<Weather>?,
) {

    constructor(
        cityId: Int?,
        cityName: String?,
        dt: Long,
        sunrise: Long,
        sunset: Long,
        pressure: Long,
        humidity: Long,
        speed: Float,
        deg: Float,
        clouds: Float,
        pop: Float,
        temp: Temp,
        feels_like: FeelsLike,
    ) : this(
        cityId,
        cityName,
        dt,
        sunrise,
        sunset,
        pressure,
        humidity,
        speed,
        deg,
        clouds,
        pop,
        temp,
        feels_like,
        null
    )

    data class Temp(
        @ColumnInfo(name = "temp_day")
        var day: Float,
        var min: Float,
        var max: Float,
        @ColumnInfo(name = "temp_night")
        var night: Float,
        @ColumnInfo(name = "temp_eve")
        var eve: Float,
        @ColumnInfo(name = "temp_morn")
        var morn: Float
    )

    data class FeelsLike(
        var day: Float,
        var night: Float,
        var eve: Float,
        var morn: Float
    )

    @Entity
    data class Weather(
        var id: Int?,
        @PrimaryKey
        var dt: Long?,
        var cityName: String?,
        var main: String?,
        var description: String?,
        var icon: String?
    ) {
        constructor(
            id: Int?,
            dt: Long?,
            cityName: String?,
            main: String?,
            description: String?,
        ) : this(id, dt, cityName, main, description, null)
    }
}
