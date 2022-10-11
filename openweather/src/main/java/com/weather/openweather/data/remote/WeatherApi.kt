package com.weather.openweather.data.remote

import com.weather.openweather.data.remote.dto.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("lat") latitude : String, @Query("lon") longitude : String, @Query("appid") apiKey : String) : Response<WeatherData>
}