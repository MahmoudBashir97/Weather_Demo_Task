package com.mahmoudbashir.weathertask_app.pojo

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    val temp: Float = 0.toFloat(),
    @SerializedName("feels_like")
    val feels_like: Float = 0.toFloat(),
    @SerializedName("humidity")
    val humidity: Float = 0.toFloat(),
    @SerializedName("pressure")
    val pressure: Float = 0.toFloat(),
    @SerializedName("temp_min")
    val temp_min: Float = 0.toFloat(),
    @SerializedName("temp_max")
    val temp_max: Float = 0.toFloat(),
    @SerializedName("sea_level")
    val sea_level:Float=0.toFloat(),
    @SerializedName("grnd_level")
    val grnd_level:Float=0.toFloat(),
    @SerializedName("temp_kf")
    val temp_kf:Float=0.toFloat(),
)
