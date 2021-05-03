package com.mahmoudbashir.weathertask_app.pojo.currentWeather

import com.google.gson.annotations.SerializedName

data class Current_Sys(
    @SerializedName("country")
    val country:String,
    @SerializedName("type")
    val type:Int?,
    @SerializedName("id")
    val id:Int?,
    @SerializedName("sunrise")
    val sunrise: Long = 0,
    @SerializedName("sunset")
    val sunset: Long = 0
)
