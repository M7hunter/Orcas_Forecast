package com.m7.orcasforecast.data.repo

import com.m7.orcasforecast.data.api.APIsImpl
import com.m7.orcasforecast.data.db.ForecastDao
import com.m7.orcasforecast.data.model.MainRes
import com.m7.orcasforecast.util.Logger
import javax.inject.Inject

class ForecastRepo @Inject constructor(
    private val apIsImpl: APIsImpl,
    private val forecastDao: ForecastDao
) {

    suspend fun getForecast(cityName: String, count: Int, apiKey: String) =
        apIsImpl.getForecast(cityName, count, apiKey)

    suspend fun getForecastFromDB(cityName: String) =
        forecastDao.getForecastsByCity(cityName).also { Logger.d("list", "$it") }

    suspend fun saveForecastToDB(mainRes: MainRes) {
        // get the cityName from city object
        mainRes.city?.let { city ->
            forecastDao.apply {
                // add cityId & cityName to mainRes to retrieve it from db by name later &
                // connect it to it's child forecast
                mainRes.cityId = city.id
                mainRes.cityName = city.name
                // save mainRes
                insertMainRes(mainRes)

                mainRes.list?.let { forecasts ->
                    forecasts.forEach { forecast ->
                        // add cityId & cityName to each forecast to connect it to parent mainRes & child weather
                        forecast.cityId = city.id
                        forecast.cityName = city.name

                        forecast.weather?.let { weathers ->
                            weathers.onEach { weather ->
                                // add dt & cityName to each weather to connect it to parent forecast
                                weather.dt = forecast.dt
                                weather.cityName = city.name
                            }
                            // save weathers
                            insertWeathers(weathers)
                        }
                    }
                    // save forecasts
                    insertForecasts(forecasts)
                }
            }
        }
    }
}