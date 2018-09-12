package com.appli.picot.meteoapp.weather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class WeatherActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, WeatherFragment.newInstance())
                .commit()
    }
}