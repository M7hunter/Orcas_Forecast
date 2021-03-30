package com.m7.orcasforecast.ui.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.m7.orcasforecast.R
import com.m7.orcasforecast.data.model.Forecast
import com.m7.orcasforecast.data.model.relatedModels.ForecastAndWeathers
import com.m7.orcasforecast.databinding.ItemForecastBinding
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(
    val data: List<ForecastAndWeathers>
) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_forecast,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(val itemBinding: ItemForecastBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(itemData: ForecastAndWeathers) {
            itemBinding.weather = itemData.weathers?.firstOrNull()
            itemBinding.date = itemData.forecast.dt?.let { getDateFromMillis(it) }
        }
    }

    private fun getDateFromMillis(millis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(calendar.time)
    }
}
