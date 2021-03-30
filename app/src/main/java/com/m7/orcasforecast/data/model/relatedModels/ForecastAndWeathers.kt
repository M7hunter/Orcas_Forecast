package com.m7.orcasforecast.data.model.relatedModels

import androidx.room.Embedded
import androidx.room.Relation
import com.m7.orcasforecast.data.model.Forecast

data class ForecastAndWeathers(
    @Embedded
    val forecast: Forecast,

    @Relation(
        parentColumn = "dt",
        entityColumn = "dt"
    )
    val weathers: List<Forecast.Weather>? = emptyList()
)