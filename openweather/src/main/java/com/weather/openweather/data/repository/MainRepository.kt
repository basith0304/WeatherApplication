package com.weather.openweather.data.repository

import com.weather.openweather.data.remote.dto.WeatherData
import retrofit2.Response

interface MainRepository {
    suspend fun getWeather(latitude: String,longitude:String): Response<WeatherData>
}