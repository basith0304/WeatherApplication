package com.weather.openweather.data.repository

import com.weather.openweather.data.local.WeatherDao
import com.weather.openweather.data.local.model.WeatherData
import retrofit2.Response
import javax.inject.Inject


abstract class LocalDbRepository @Inject constructor(private val weatherDao: WeatherDao) : MainRepository {

override suspend fun getSavedWeather(latitude: String, longitude:String): List<WeatherData>{
        return weatherDao.getWeatherData(latitude.toDouble(),longitude.toDouble())
    }

}
