package com.weather.openweather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.openweather.data.local.model.WeatherData

@Dao
interface WeatherDao {

    @Query("SELECT * from weatherData WHERE latitude = :latitude AND longitude =:longitude")
    suspend fun getWeatherData(latitude: Double,longitude :Double): List<WeatherData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherData): Long

//    @Query("DELETE FROM weatherData")
//    suspend fun deleteAllWeatherData(): Int

// Next methods are created specially for instrumental unit-tests
//    @Query("DELETE FROM weatherData WHERE id = (SELECT MAX(id) FROM weatherData)")
//    suspend fun deleteLastItem(): Int

    @Query("SELECT COUNT(*) FROM weatherData")
    suspend fun countOfRows(): Int
}