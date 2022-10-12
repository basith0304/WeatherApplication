package com.weather.openweather.data.repository


import com.weather.openweather.BuildConfig
import com.weather.openweather.data.local.WeatherDao
import com.weather.openweather.data.remote.WeatherApi
import com.weather.openweather.data.remote.dto.WeatherData
import retrofit2.Response
import javax.inject.Inject


class NetworkRepository @Inject constructor(private val api: WeatherApi): MainRepository {

    override suspend fun getWeather(latitude: String,longitude:String): Response<WeatherData> {
       return api.getWeather(latitude.toString(),longitude.toString(),BuildConfig.API_KEY)
    }
}