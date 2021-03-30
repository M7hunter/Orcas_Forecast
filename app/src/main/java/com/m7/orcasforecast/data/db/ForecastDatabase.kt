package com.m7.orcasforecast.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.m7.orcasforecast.data.model.Forecast
import com.m7.orcasforecast.data.model.MainRes

@Database(
    entities = [MainRes::class, Forecast::class, Forecast.Weather::class],
    exportSchema = true,
    version = 1
)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    companion object {

        @Volatile
        private var INSTANCE: ForecastDatabase? = null

        fun getInstance(context: Context): ForecastDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context): ForecastDatabase =
            Room.databaseBuilder(
                context,
                ForecastDatabase::class.java,
                "ForecastDatabase"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}