package com.mahmoudbashir.weathertask_app.repository

import androidx.lifecycle.LiveData

import com.mahmoudbashir.weathertask_app.pojo.currentWeather.CurrentWeather_Model
import com.mahmoudbashir.weathertask_app.pojo.forecastWeather.WeatherModel
import retrofit2.Call

interface RepoMethodsInterface {

    suspend fun getCurrentWeather(lat:Double,lng: Double,appKey:String,units:String):Call<CurrentWeather_Model>
    suspend fun getForeCast(q:String,appKey:String,units:String):Call<WeatherModel>

    suspend fun insertCurrentWeather(currentweatherModel: CurrentWeather_Model)
    fun getStoredWeatherFromRoom():LiveData<CurrentWeather_Model>

    suspend fun insertForecastWeather(weatherModel: WeatherModel)
    fun getStoredForecastFromRoom():LiveData<List<WeatherModel>>
    suspend fun deleteCityForecast(weatherModel: WeatherModel)
}