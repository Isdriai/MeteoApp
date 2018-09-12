package com.appli.picot.meteoapp.weather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.appli.picot.meteoapp.App
import com.appli.picot.meteoapp.R
import com.appli.picot.meteoapp.openweathermap.Weather
import com.appli.picot.meteoapp.openweathermap.WeatherWrapper
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment: Fragment() {

    companion object {
        val TAG = WeatherWrapper::class.java.simpleName
        val EXTRA_CITY_NAME = "training.kotlin.weather.extras.EXTRA_CITY_NAME"
        fun newInstance() : WeatherFragment {
            Log.i(WeatherFragment.TAG, "new instance")
            return WeatherFragment()
        }
    }

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var city: TextView
    private lateinit var cityName: String
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherDescription: TextView
    private lateinit var temperature: TextView
    private lateinit var pressure: TextView
    private lateinit var humidity: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG, "oncreatedview")
        val view = inflater.inflate(R.layout.weather_fragment, container, false)

        refreshLayout = view.findViewById(R.id.swipeRefresh)
        city = view.findViewById(R.id.city)
        weatherIcon = view.findViewById(R.id.weather_icon)
        weatherDescription = view.findViewById(R.id.weather_description)
        temperature = view.findViewById(R.id.temperature)
        humidity = view.findViewById(R.id.humidity)
        pressure = view.findViewById(R.id.pressure)

        refreshLayout.setOnRefreshListener { refreshWeather() }

        return view
    }

    private fun refreshWeather(){
        updateWeatherForCity(cityName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity?.intent!!.hasExtra(EXTRA_CITY_NAME)){
            updateWeatherForCity(activity!!.intent.getStringExtra(EXTRA_CITY_NAME))
        }
    }

    fun updateWeatherForCity(cityName: String){
        this.cityName = cityName
        this.city.text = cityName

        if(!refreshLayout.isRefreshing){
            refreshLayout.isRefreshing = true
        }

        val call = App.weatherService.getWeather("$cityName,fr")
        call.enqueue(object: Callback<WeatherWrapper> {
            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Log.e(TAG, "could not load city weather", t)
                Toast.makeText(activity, getString(R.string.message_error_coul_not_load_city), Toast.LENGTH_SHORT). show()
                refreshLayout.isRefreshing = false
            }

            override fun onResponse(call: Call<WeatherWrapper>, response: Response<WeatherWrapper>) {

                response?.body()?.let {
                    val weather = mapOpenWeatherDataToWeather(it)
                    updateUi(weather)
                    Log.i(TAG, "onResponse Weatherfregment call: ${weather}")
                    refreshLayout.isRefreshing = false
                }
            }

        })
    }

    private fun updateUi(weather: Weather){

        Picasso.get().load(weather.iconUrl).placeholder(R.drawable.ic_launcher_background).into(weatherIcon)

        weatherDescription.text = weather.description
        temperature.text = getString(R.string.weather_temperature_value, weather.temperature.toInt())
        humidity.text = getString(R.string.weather_humidity_value, weather.humidity)
        pressure.text = getString(R.string.weather_pressure_value, weather.pressure)
    }

    fun clearUI(){
        weatherIcon.setImageResource(R.drawable.abc_ic_ab_back_material)
        cityName = ""
        city.text = ""
        weatherDescription.text = ""
        humidity.text =""
        pressure.text = ""
        temperature.text = ""

    }
}