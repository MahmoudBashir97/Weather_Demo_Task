package com.mahmoudbashir.weathertask_app.pojo.currentWeather

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mahmoudbashir.weathertask_app.pojo.*

@Entity(tableName = "current_weather_table")
data class CurrentWeather_Model (
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("weather")
    val weather:List<Weather>,
    @SerializedName("base")
    val base:String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("visibility")
    val visibility:Float = 0.toFloat(),
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("dt")
    val dt: Float = 0.toFloat(),
    @SerializedName("sys")
    val sys:Current_Sys,
    @SerializedName("timezone")
    val timezone:Int,
    @SerializedName("id")
    @PrimaryKey
    val id:Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("cod")
    val cod:Int
    )
