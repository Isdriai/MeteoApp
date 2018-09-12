package com.appli.picot.meteoapp.weather

import com.appli.picot.meteoapp.openweathermap.Weather
import com.appli.picot.meteoapp.openweathermap.WeatherWrapper

fun mapOpenWeatherDataToWeather(weatherWrapper: WeatherWrapper): Weather {
    val weatherFirst = weatherWrapper.weather.first()
    return Weather( description = weatherFirst.description,
            temperature = weatherWrapper.main.temperature,
            humidity = weatherWrapper.main.humidity,
            pressure = weatherWrapper.main.pressure,
            iconUrl = "https://openweathermap.org/img/w/${weatherFirst.icon}.png" )
}