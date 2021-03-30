package com.m7.orcasforecast.data.model

import androidx.room.Embedded

data class City(
    var id: Int,
    var name: String,
    var country: String?,
    var population: Int?,
    var timezone: Int?,
    @Embedded
    var coordinate: Coordinate?
) {
    data class Coordinate(
        var lon: Long?,
        var lat: Long?,
    )
}
