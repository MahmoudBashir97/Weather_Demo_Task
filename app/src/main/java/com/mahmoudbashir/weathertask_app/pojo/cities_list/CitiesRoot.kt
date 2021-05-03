package com.mahmoudbashir.weathertask_app.pojo.cities_list

import com.google.gson.annotations.SerializedName


data class CitiesRoot(
    @SerializedName("message")
    val message:String,
    @SerializedName("code")
    val code:Int,
    @SerializedName("cities")
    val cities: List<Cities>
)
