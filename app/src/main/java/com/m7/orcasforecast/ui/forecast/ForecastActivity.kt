package com.m7.orcasforecast.ui.forecast

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.m7.orcasforecast.base.BaseActivity
import com.m7.orcasforecast.R
import com.m7.orcasforecast.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastActivity : BaseActivity() {

    private lateinit var forecastViewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        forecastViewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)

        viewBinding.apply {
            noCity = true
            etSearch.doOnTextChanged { text, _, _, _ ->
                noCity = text.isNullOrBlank()
            }

            ivSearch.setOnClickListener {
                if (etSearch.text.isNotBlank())
                    forecastViewModel.getForecastData(etSearch.text.toString().trim(), 7)
            }

            btnTryAgain.setOnClickListener {
                ivSearch.performClick()
            }

            // prepare rv
            rvForecast.layoutManager = LinearLayoutManager(this@ForecastActivity)
        }

        setupObservers()
    }

    private fun setupObservers() {
        forecastViewModel.forecastData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    displayLoading()
                }
                Status.SUCCESS -> {
                    displayLoading(false)

                    it.data?.let { forecastList ->
                        viewBinding.apply {
                            failed = false
                            notAccurate = false
                            rvForecast.adapter = ForecastAdapter(forecastList)
                        }
                    }
                }
                Status.ERROR -> {
                    displayLoading(false)
                    displayError(it.msg ?: getString(it.redId ?: R.string.error_occurred))
                    viewBinding.apply {
                        if (it.data != null) {
                            failed = false
                            notAccurate = true
                            rvForecast.adapter = ForecastAdapter(it.data)
                        } else {
                            failed = true
                            notAccurate = false
                        }
                    }
                }
            }
        }
    }
}