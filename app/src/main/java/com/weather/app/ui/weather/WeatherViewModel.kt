package com.weather.app.ui.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.weather.openweather.common.Resource
import com.weather.openweather.data.remote.dto.WeatherData
import com.weather.openweather.domain.usecase.weather.WeatherUIState
import com.weather.openweather.domain.usecase.weather.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherUIState>(WeatherUIState.Empty)
    val weatherState: StateFlow<WeatherUIState> = _weatherState

    fun getWeather(latitude: String, longitude: String){

        weatherUseCase(latitude,longitude).onEach { result->

            when(result){

                is Resource.Success ->{
                    _weatherState.value = WeatherUIState.Success(result.data)
                }
                is Resource.Error ->{
                    if(result.data!=null)
                        _weatherState.value = WeatherUIState.Error(result.data?.message ?: " An unexpected error")
                    else
                        _weatherState.value = WeatherUIState.Error(result.message?: " An unexpected error")
                }

                is Resource.Loading ->{
                    _weatherState.value = WeatherUIState.Loading
                }
            }
        }.launchIn(viewModelScope)

    }

}