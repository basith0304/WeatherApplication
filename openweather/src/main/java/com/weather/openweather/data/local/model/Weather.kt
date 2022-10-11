package com.weather.openweather.data.local.model

import androidx.room.*

@Entity(tableName = "weatherData")
data class Weather(

    @PrimaryKey(autoGenerate=true)
    val id: String,

    @ColumnInfo(name = "latitude") val latitude: Double ?,
    @ColumnInfo(name = "longitude") val longitude: Double ?,
    @ColumnInfo(name = "weather") val weather: String ?,
    @ColumnInfo(name = "tempMin") val tempMin: Double ?,
    @ColumnInfo(name = "tempMax") val tempMax: Double ?,
    @ColumnInfo(name = "humidity") val humidity: Int ?,
    @ColumnInfo(name = "feelsLike") val feelsLike: Double ?,
    @ColumnInfo(name = "visibility") val visibility: Long ?,
    @ColumnInfo(name = "sunrise") val sunrise: Long ?,
    @ColumnInfo(name = "sunset") val sunset: Long ?,
    @ColumnInfo(name = "windSpeed") val windSpeed: Double ?
)