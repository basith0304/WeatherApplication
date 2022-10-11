package com.weather.openweather.data.repository

import com.weather.openweather.data.remote.dto.WeatherData
import retrofit2.Response

interface MainRepository {
    suspend fun getWeather(latitude: String,longitude:String): Response<WeatherData>

    suspend fun saveWeather(weather: com.weather.openweather.data.local.model.WeatherData): Long

    suspend fun getSavedWeather(latitude: String,longitude:String): List<com.weather.openweather.data.local.model.WeatherData>
}