package com.m7.orcasforecast.data.db

import androidx.room.*
import com.m7.orcasforecast.data.model.Forecast
import com.m7.orcasforecast.data.model.MainRes
import com.m7.orcasforecast.data.model.relatedModels.MainResAndForecasts

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMainRes(mainRes: MainRes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecasts(forecasts: List<Forecast>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeathers(weathers: List<Forecast.Weather>)

    @Transaction
    @Query("SELECT * FROM MainRes WHERE cityName LIKE :cityName")
    suspend fun getForecastsByCity(cityName: String): MainResAndForecasts?
}