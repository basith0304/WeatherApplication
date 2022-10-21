package com.weather.app.ui.weather

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.weather.app.R
import com.weather.app.databinding.ActivityMainBinding
import com.weather.app.ui.settings.SettingsActivity
import com.weather.app.ui.weather.adapters.WeatherInfoAdapter
import com.weather.app.ui.weather.model.WeatherInfoModel
import com.weather.openweather.domain.usecase.weather.WeatherUIState
import dagger.hilt.android.AndroidEntryPoint
import java.security.Timestamp
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() , LocationListener {

    private val viewModel: WeatherViewModel by viewModels()
    private  val TAG = "WeatherActivity"
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private val mContext:Context=this

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val recyclerView=binding.recycleWeatherInfo
        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)


        /** Call this method to get current weather data */
        getLocation()

        lifecycleScope.launchWhenStarted {
            viewModel.weatherState.collect {
                when(it){
                    is WeatherUIState.Loading ->{
                        Log.i(TAG,"Loading...")
                    }
                    is WeatherUIState.Success ->{
                        val tmp = 0x00B0.toChar()
                        binding.txtWeather.text=((((it.data?.main!!.temp - 273)).roundToInt()).toString()+tmp+"C")
                        binding.txtCity.text=it.data!!.name

                        val weatherInfo = arrayListOf<WeatherInfoModel>()

                        /**
                         * Minimum Temperature
                         * Maximum Temperature
                         * Humidity
                         * Visibility
                         * Sunrise
                         * Sunset
                         * Windspeed
                         * **/

                       // var tsLong: Int = it.data?.sys?.sunset/1000
                        val ts = Date(it.data?.sys?.sunset!!.toLong())

                        weatherInfo.add(WeatherInfoModel(((it.data?.main!!.tempMin - 273).roundToInt()).toString()+tmp+"C",ContextCompat.getDrawable(mContext, R.drawable.temp_min)))
                        weatherInfo.add(WeatherInfoModel(((it.data?.main!!.tempMax - 273).roundToInt()).toString()+tmp+"C",ContextCompat.getDrawable(mContext, R.drawable.temp_max)))
                        weatherInfo.add(WeatherInfoModel(it.data?.main?.humidity.toString(),ContextCompat.getDrawable(mContext, R.drawable.humidity)))
                        weatherInfo.add(WeatherInfoModel(it.data?.main?.feelsLike.toString(),ContextCompat.getDrawable(mContext, R.drawable.feels_like)))
                        weatherInfo.add(WeatherInfoModel((it.data?.visibility!!/1000).toString()+" Km",ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_remove_red_eye_24)))
                        weatherInfo.add(WeatherInfoModel(ts.toString(),ContextCompat.getDrawable(mContext, R.drawable.sunset)))
                        weatherInfo.add(WeatherInfoModel(it.data?.sys?.sunrise.toString(),ContextCompat.getDrawable(mContext, R.drawable.sunrise)))
                        weatherInfo.add(WeatherInfoModel(it.data?.wind!!.speed.toString()+" m/s",ContextCompat.getDrawable(mContext, R.drawable.wind_speed)))

                        val weatherAdapter = WeatherInfoAdapter(weatherInfo)

                        recyclerView.adapter=weatherAdapter
                        recyclerView.layoutManager = layoutManager
                        recyclerView.setHasFixedSize(true)
                        recyclerView.addItemDecoration(
                            DividerItemDecoration(
                                mContext,
                                layoutManager.orientation
                            )
                        )

                        Log.i(TAG,"SUCCESS")
                    }

                    is WeatherUIState.Error ->{
                        Log.i(TAG,"Error:${it}")
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    /** Menu for Settings and Favourites */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_fav -> {
                Toast.makeText(applicationContext, "click on Fav", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_settings ->{
                startActivity(Intent(this@WeatherActivity, SettingsActivity::class.java))
                 true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /** Use this function to Get the User's Current Location to get the Current Weather.*/
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    override fun onLocationChanged(location: Location) {
        Log.i(TAG,"Location Triggered {${location.latitude},${location.longitude}}")
        viewModel.getWeather(location.latitude.toString(),location.longitude.toString())
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }
}