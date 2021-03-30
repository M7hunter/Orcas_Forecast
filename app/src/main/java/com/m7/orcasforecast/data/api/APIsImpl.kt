package com.m7.orcasforecast.data.api

import javax.inject.Inject

class APIsImpl @Inject constructor(private val apIs: APIs) {

    suspend fun getForecast(cityName: String, count: Int, apiKey: String) =
        apIs.getForecast(cityName, count, apiKey)
}