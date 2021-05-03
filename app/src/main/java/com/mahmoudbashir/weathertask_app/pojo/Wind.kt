package com.mahmoudbashir.weathertask_app.pojo

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    val speed: Float = 0.toFloat(),
    @SerializedName("deg")
    val deg: Float = 0.toFloat(),
    @SerializedName("gust")
    val gust:Float=0.toFloat()
)
