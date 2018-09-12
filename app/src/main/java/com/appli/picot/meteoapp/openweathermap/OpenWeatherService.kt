package com.appli.picot.meteoapp.openweathermap

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY ="900464b7fda4d1c0395e734efbbf9102"

interface OpenWeatherService {

    @GET("data/2.5/weather?units=metric&lang=fr")
    fun getWeather(@Query("q") cityName: String,
                    @Query("appid") apiKey: String = API_KEY): Call<WeatherWrapper>
}