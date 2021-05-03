package com.mahmoudbashir.weathertask_app.pojo

import com.google.gson.annotations.SerializedName

data class _Rain (
    @SerializedName("3h")
    val h3: Float = 0.toFloat()
)