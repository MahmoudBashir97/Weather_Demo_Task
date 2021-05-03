package com.mahmoudbashir.weathertask_app.di.components

import com.mahmoudbashir.weathertask_app.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface ComponentsWeather {
    fun inject(activity: MainActivity)
}