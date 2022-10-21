package com.weather.openweather.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "feels_like")
    val feelsLike: Double=0.0,
    @Json(name = "grnd_level")
    val grndLevel: Int=0,
    @Json(name = "humidity")
    val humidity: Int=0,
    @Json(name = "pressure")
    val pressure: Int=0,
    @Json(name = "sea_level")
    val seaLevel: Int=0,
    @Json(name = "temp")
    val temp: Double=0.0,
    @Json(name = "temp_max")
    val tempMax: Double=0.0,
    @Json(name = "temp_min")
    val tempMin: Double=0.0,
   

)