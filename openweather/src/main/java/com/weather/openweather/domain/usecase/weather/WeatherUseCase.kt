package com.weather.openweather.domain.usecase.weather

import com.weather.openweather.common.Constants
import com.weather.openweather.common.Resource
import com.weather.openweather.data.remote.dto.WeatherData
import com.weather.openweather.data.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(latitude:String,longitude:String) : Flow<Resource<WeatherData?>> = flow {
        try{
            emit(Resource.Loading())
            val weatherData=repository.getWeather(latitude,longitude)
            if(weatherData.body()?.cod== Constants.API_RESULT_SUCCESS){
                emit(Resource.Success(weatherData.body()))
            } else {
                emit(Resource.Error(weatherData.body()?.message?: weatherData.body()?.message?:"An unexpected error occurred"))
            }

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage?: "An unexpected error occurred"))
        }
        catch (e : IOException){
            emit(Resource.Error(e.localizedMessage?: "An unexpected error occurred"))
        }
        catch (e : Exception){
            emit(Resource.Error(e.localizedMessage?: "An unexpected error occurred"))
        }

    }

}