package com.mahmoudbashir.weathertask_app.repository

import androidx.lifecycle.LiveData
import com.mahmoudbashir.weathertask_app.Retrofit.RetrofitInstance
import com.mahmoudbashir.weathertask_app.pojo.cities_list.CitiesRoot
import com.mahmoudbashir.weathertask_app.pojo.currentWeather.CurrentWeather_Model
import com.mahmoudbashir.weathertask_app.pojo.forecastWeather.WeatherModel
import com.mahmoudbashir.weathertask_app.room.WeatherRoomDatabase
import retrofit2.Call

class WeatherRepository(
    private val db:WeatherRoomDatabase
):RepoMethodsInterface{
    override suspend fun getCurrentWeather(
        lat:Double,
        lng:Double,
        appKey: String,
        units:String
    ): Call<CurrentWeather_Model> {
       return RetrofitInstance.api.getCurrentWeatherData(lat,lng,appKey,units)
    }

    override suspend fun getForeCast(q: String, appKey: String,units:String): Call<WeatherModel> {
        return RetrofitInstance.api.getForecastWeatherData(q,appKey,units)
    }

    override suspend fun insertCurrentWeather(currentweatherModel: CurrentWeather_Model)=db.dao().insertCurrentWeather(currentweatherModel)

    override fun getStoredWeatherFromRoom(): LiveData<CurrentWeather_Model> = db.dao().getStoredWeather()

    override suspend fun insertForecastWeather(weatherModel: WeatherModel) = db.dao().insertForecastWeather(weatherModel)

    override fun getStoredForecastFromRoom(): LiveData<List<WeatherModel>> = db.dao().getStoredForecast()
    override suspend fun deleteCityForecast(weatherModel: WeatherModel) = db.dao().deleteCityForecast(weatherModel)
}