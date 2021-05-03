package com.mahmoudbashir.weathertask_app.Retrofit

import com.mahmoudbashir.weathertask_app.pojo.cities_list.CitiesRoot
import com.mahmoudbashir.weathertask_app.pojo.currentWeather.CurrentWeather_Model
import com.mahmoudbashir.weathertask_app.pojo.forecastWeather.WeatherModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {

    @GET("data/2.5/forecast")
     fun getForecastWeatherData(@Query("q") q:String , @Query("appid") appid:String,@Query("units") units:String):Call<WeatherModel>

    @GET("data/2.5/weather")
    fun getCurrentWeatherData(@Query("lat") lat:Double, @Query("lon") lng:Double, @Query("appid") appid:String, @Query("units") units:String):Call<CurrentWeather_Model>


}