package com.weather.openweather.data.local.model

data class WeatherData(val latitude: Double,
                          val longitude: Double,
                          val weather: String,
                          val tempMin: Double ,
                          val tempMax: Double,
                          val humidity: Int,
                          val feelsLike: Double,
                          val visibility: Long ,
                          val sunrise: Long,
                          val sunset: Long,
                          val windSpeed: Double ){


}
