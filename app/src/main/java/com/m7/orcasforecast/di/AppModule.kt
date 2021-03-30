package com.m7.orcasforecast.di

import android.content.Context
import com.m7.orcasforecast.BuildConfig
import com.m7.orcasforecast.data.api.APIs
import com.m7.orcasforecast.data.api.APIsImpl
import com.m7.orcasforecast.data.db.ForecastDao
import com.m7.orcasforecast.data.db.ForecastDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideForecastDao(db: ForecastDatabase): ForecastDao = db.forecastDao()

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ForecastDatabase =
        ForecastDatabase.getInstance(context)

    @Provides
    fun provideAPIsImpl(apis: APIs): APIsImpl = APIsImpl(apis)

    @Provides
    fun provideAPIs(okHttpClient: OkHttpClient): APIs = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIs::class.java)

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG)
                addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
        }.build()
}