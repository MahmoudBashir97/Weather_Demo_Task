package com.mahmoudbashir.weathertask_app.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mahmoudbashir.weathertask_app.pojo.cities_list.CitiesRoot
import com.mahmoudbashir.weathertask_app.pojo.currentWeather.CurrentWeather_Model
import com.mahmoudbashir.weathertask_app.pojo.forecastWeather.WeatherModel

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentweatherModel: CurrentWeather_Model)

    @Query("SELECT * FROM current_weather_table order by id ASC")
    fun getStoredWeather():LiveData<CurrentWeather_Model>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastWeather(weatherModel:WeatherModel)

    @Query("SELECT * FROM forecast_weather_table order by id ASC")
    fun getStoredForecast():LiveData<List<WeatherModel>>

    @Delete
    suspend fun deleteCityForecast(weatherModel: WeatherModel)
}