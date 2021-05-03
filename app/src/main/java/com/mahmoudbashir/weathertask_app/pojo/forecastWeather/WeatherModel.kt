package com.mahmoudbashir.weathertask_app.pojo.forecastWeather

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mahmoudbashir.weathertask_app.pojo.City
import com.mahmoudbashir.weathertask_app.pojo.InfoList
import com.mahmoudbashir.weathertask_app.room.converters.Converters

@Entity(tableName = "forecast_weather_table")
data class WeatherModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("list")
    val list: List<InfoList>,
    @SerializedName("message")
    val message: Int,
    @SerializedName("city")
    val city: City
)