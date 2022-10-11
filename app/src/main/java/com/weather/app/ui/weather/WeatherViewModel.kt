package com.weather.app.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.app.BuildConfig
import com.weather.app.BuildConfig.API_KEY

import com.weather.openweather.common.Resource
import com.weather.openweather.domain.usecase.weather.WeatherUseCase
import com.weather.openweather.domain.usecase.weather.WeatherViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {


    private val _weatherData = MutableStateFlow(WeatherViewState())
    val weatherData: StateFlow<WeatherViewState> = _weatherData

    fun getWeather(latitude: String, longitude: String){

        weatherUseCase(latitude,longitude).onEach { result->

            when(result){

                is Resource.Success ->{
                    _weatherData.value = WeatherViewState(response = result.data)
                }

                is Resource.Error ->{
                    _weatherData.value = WeatherViewState(error = result.data?.message ?: " An unexpected error")
                }

                is Resource.Loading ->{
                    _weatherData.value = WeatherViewState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

}