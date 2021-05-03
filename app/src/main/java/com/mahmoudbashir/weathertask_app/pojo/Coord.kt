package com.mahmoudbashir.weathertask_app.pojo

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lon")
    val lon: Float = 0.toFloat(),
    @SerializedName("lat")
    val lat: Float = 0.toFloat()
)
