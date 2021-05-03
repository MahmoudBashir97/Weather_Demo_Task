package com.mahmoudbashir.weathertask_app.pojo

import com.google.gson.annotations.SerializedName

data class InfoList(
    @SerializedName("main")
    val main: Main,
    @SerializedName("dt")
    val dt: Float = 0.toFloat(),
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("cloud")
    val clouds: Clouds,
    @SerializedName("rain")
    val rain: _Rain,
    @SerializedName("snow")
    val snow: _Snow,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("dt_txt")
    val dt_txt:String,
    @SerializedName("visibility")
    val visibility:Double,
    @SerializedName("pop")
    val pop:Float=0.toFloat()
)


