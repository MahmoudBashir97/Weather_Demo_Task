package com.mahmoudbashir.weathertask_app.pojo

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Float = 0.toFloat()
)
