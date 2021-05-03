package com.mahmoudbashir.weathertask_app.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahmoudbashir.weathertask_app.pojo.currentWeather.CurrentWeather_Model
import com.mahmoudbashir.weathertask_app.pojo.forecastWeather.WeatherModel
import com.mahmoudbashir.weathertask_app.room.converters.Converters

@Database(entities = [CurrentWeather_Model::class,WeatherModel::class],version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherRoomDatabase:RoomDatabase() {
    abstract fun dao():WeatherDao
}