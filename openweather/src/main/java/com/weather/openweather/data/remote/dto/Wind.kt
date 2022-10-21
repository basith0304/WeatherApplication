package com.weather.openweather.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "deg")
    val deg: Int=0,
    @Json(name = "gust")
    val gust: Double=0.0,
    @Json(name = "speed")
    val speed: Double=0.0
)