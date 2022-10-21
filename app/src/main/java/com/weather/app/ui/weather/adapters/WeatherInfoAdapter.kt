package com.weather.app.ui.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.app.databinding.ItemWeatherInfoBinding
import com.weather.app.ui.weather.model.WeatherInfoModel

class WeatherInfoAdapter(
    private val WeatherInfoList: List<WeatherInfoModel>
) : RecyclerView.Adapter<WeatherInfoViewHolder>() {

    private lateinit var binding: ItemWeatherInfoBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoViewHolder {
        binding = ItemWeatherInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherInfoViewHolder, position: Int) {
        val weatherInfo = WeatherInfoList[position]
        holder.bind(weatherInfo)
    }

    override fun getItemCount(): Int = WeatherInfoList.size

}

class WeatherInfoViewHolder(
    private val binding: ItemWeatherInfoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(weatherInfo: WeatherInfoModel) {
        binding.weatherInfo = weatherInfo
    }
}
