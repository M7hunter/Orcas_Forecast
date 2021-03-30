package com.m7.orcasforecast.data.model.relatedModels

import androidx.room.Embedded
import androidx.room.Relation
import com.m7.orcasforecast.data.model.Forecast
import com.m7.orcasforecast.data.model.MainRes

data class MainResAndForecasts(
    @Embedded
    val mainRes: MainRes,

    @Relation(
        parentColumn = "id",
        entityColumn = "cityId",
        entity = Forecast::class
    )
    val forecasts: List<ForecastAndWeathers>? = emptyList()
)