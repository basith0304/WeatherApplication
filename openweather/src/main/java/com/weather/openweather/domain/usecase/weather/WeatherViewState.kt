package com.weather.openweather.domain.usecase.weather

import com.weather.openweather.data.remote.dto.WeatherData
import retrofit2.Response

 sealed class WeatherUIState {
        data class Success(val data: WeatherData?) : WeatherUIState()
        data class Error(val message: String) : WeatherUIState()
        object Loading : WeatherUIState()
        object Empty : WeatherUIState()
}
