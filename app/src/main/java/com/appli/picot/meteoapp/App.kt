package com.appli.picot.meteoapp

import android.app.Application
import android.util.Log

class App: Application() {

    companion object {
        lateinit var instance : App

        val database: DataBase by lazy {
            DataBase(instance)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}