package com.m7.orcasforecast.data.api

import com.m7.orcasforecast.data.model.MainRes
import retrofit2.Response
import retrofit2.http.*

interface APIs {

    @GET("daily")
    suspend fun getForecast(
        @Query("q") cityName: String,
        @Query("cnt") count: Int,
        @Query("appid") apiKey: String,
    ): Response<MainRes>
}