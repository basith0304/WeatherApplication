package com.weather.app.ui.weather

import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.weather.app.R
import com.weather.app.databinding.ActivityMainBinding
import com.weather.openweather.domain.usecase.weather.WeatherViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private  val TAG = "WeatherActivity"

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.getWeather("21.482814","39.184695")

    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launchWhenStarted {

            viewModel.weatherData.value.let {

                it.isLoading.let {
                    Log.i(TAG,"Loading...")

                }

                it.response?.let {
                    Log.i(TAG,it.message)
                }

                it.error.let {

                    Log.i(TAG,it)
                }
            }
        }
    }
}