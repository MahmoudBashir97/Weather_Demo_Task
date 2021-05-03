package com.mahmoudbashir.weathertask_app.pojo

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id")
    val id:Double,
    @SerializedName("name")
    val name:String,
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("country")
    val country:String,
    @SerializedName("population")
    val population:Double,
    @SerializedName("timezone")
    val timezone:Double,
    @SerializedName("sunrise")
    val sunrise:Double,
    @SerializedName("sunset")
    val sunset:Double,
)
