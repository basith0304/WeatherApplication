package com.weather.openweather.domain.usecase.weather

import com.weather.openweather.data.remote.dto.WeatherData
import retrofit2.Response

data class WeatherViewState(
    var isLoading: Boolean =false,
    var response: WeatherData? = null,
    var error: String =""
)

