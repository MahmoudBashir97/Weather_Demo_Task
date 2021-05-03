package com.mahmoudbashir.weathertask_app

import android.app.Application
import com.mahmoudbashir.weathertask_app.di.components.ComponentsWeather
import com.mahmoudbashir.weathertask_app.di.components.DaggerComponentsWeather

class WeatherApplication:Application() {

    companion object{
        lateinit var instance:WeatherApplication
    }

    lateinit var weather_Comp:ComponentsWeather
    override fun onCreate() {
        super.onCreate()
        instance = this
        weather_Comp = DaggerComponentsWeather
            .builder().build()

    }
}