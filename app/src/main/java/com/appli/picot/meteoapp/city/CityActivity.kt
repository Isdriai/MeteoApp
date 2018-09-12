package com.appli.picot.meteoapp.city

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.appli.picot.meteoapp.R
import com.appli.picot.meteoapp.weather.WeatherActivity
import com.appli.picot.meteoapp.weather.WeatherFragment

class CityActivity : AppCompatActivity(), CityFragment.CityFragmentListener {



    private lateinit var cityFragment: CityFragment

    private var weatherFragment: WeatherFragment? =null
    private var currentCity: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.city_activity)

        cityFragment = supportFragmentManager.findFragmentById(R.id.cityFragment) as CityFragment

        cityFragment.listener = this

        weatherFragment = supportFragmentManager.findFragmentById(R.id.weather_fragment) as WeatherFragment
    }

    override fun onCitySelected(city: City) {
        currentCity = city

        if (isHandsetLayout()) {
            startWeatherActivity(city)
        }else {
            weatherFragment?.updateWeatherForCity(city.name)
        }

    }

    override fun onEmptyCities() {
        weatherFragment?.clearUI()
    }

    private fun isHandsetLayout(): Boolean{
        return weatherFragment == null
    }

    private fun startWeatherActivity(city: City) {
        Log.i("CityActivity", "lancement ")
        val intent = Intent(this, WeatherActivity::class.java)
        Log.i("CityActivity", "pret ")
        intent.putExtra(WeatherFragment.EXTRA_CITY_NAME, city.name)
        Log.i("CityActivity", "go ")
        startActivity(intent)
    }
}
