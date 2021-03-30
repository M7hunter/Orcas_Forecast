package com.m7.orcasforecast.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m7.orcasforecast.BuildConfig
import com.m7.orcasforecast.R
import com.m7.orcasforecast.data.model.Forecast
import com.m7.orcasforecast.data.model.relatedModels.ForecastAndWeathers
import com.m7.orcasforecast.data.repo.ForecastRepo
import com.m7.orcasforecast.util.ConnectivityHandler
import com.m7.orcasforecast.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val connectivityHandler: ConnectivityHandler,
    private val repo: ForecastRepo
) : ViewModel() {

    private val _forecastData = MutableLiveData<Resource<List<ForecastAndWeathers>>>()
    val forecastData: LiveData<Resource<List<ForecastAndWeathers>>> = _forecastData

    fun getForecastData(cityName: String, count: Int) {
        viewModelScope.launch((CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            _forecastData.postValue(Resource.error(msg = throwable.message.toString()))
        })) {
            if (connectivityHandler.isConnected()) {
                _forecastData.postValue(Resource.loading())
                repo.getForecast(cityName, count, BuildConfig.API_KEY).apply {
                    if (isSuccessful) {
                        body()?.let { mainRes ->
                            val forecastsAndWeathers = ArrayList<ForecastAndWeathers>()
                            mainRes.list?.forEach {
                                forecastsAndWeathers.add(ForecastAndWeathers(it, it.weather))
                            }
                            _forecastData.postValue(Resource.success(forecastsAndWeathers))
                            repo.saveForecastToDB(mainRes)
                        }
                    } else {
                        // try to find requested city in the db
                        _forecastData.postValue(
                            Resource.error(
                                repo.getForecastFromDB(cityName)?.forecasts, body()?.message
                            )
                        )
                    }
                }
            } else {
                repo.getForecastFromDB(cityName).let { localMainRes ->
                    _forecastData.postValue(
                        Resource.error(
                            localMainRes?.forecasts,
                            resId = R.string.no_internet_connection
                        )
                    )
                }
            }
        }
    }
}