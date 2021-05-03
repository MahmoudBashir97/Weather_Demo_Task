package com.mahmoudbashir.weathertask_app.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahmoudbashir.weathertask_app.pojo.*
import com.mahmoudbashir.weathertask_app.pojo.cities_list.Cities
import com.mahmoudbashir.weathertask_app.pojo.currentWeather.CurrentWeather_Model
import com.mahmoudbashir.weathertask_app.pojo.currentWeather.Current_Sys

class Converters {


    @TypeConverter
    public fun fromCityToString(city: City):String?{
        return Gson().toJson(city)
    }

    @TypeConverter
    public fun fromStringToCity(stcity: String):City?{
        return Gson().fromJson(stcity,City::class.java)
    }

    @TypeConverter
    public fun fromCoordToString(coord: Coord):String?{
        return Gson().toJson(coord)
    }

    @TypeConverter
    public fun fromStringToCoord(stcoord: String):Coord?{
        return Gson().fromJson(stcoord,Coord::class.java)
    }


    @TypeConverter
    public fun fromWeatherListToString(weather:List<Weather>):String?{
        return Gson().toJson(weather)
    }

    @TypeConverter
    public fun fromStringWeatherList(stweather:String):List<Weather>{
        val listType = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(stweather,listType)
    }

    @TypeConverter
    public fun fromMainToString(main: Main):String?{
        return Gson().toJson(main)
    }

    @TypeConverter
    public fun fromStringToMain(stmain: String):Main?{
        return Gson().fromJson(stmain,Main::class.java)
    }

    @TypeConverter
    public fun fromCurrentMainToString(main:com.mahmoudbashir.weathertask_app.pojo.currentWeather.Main):String?{
        return Gson().toJson(main)
    }

    @TypeConverter
    public fun fromStringToCurrentMain(stmain: String):com.mahmoudbashir.weathertask_app.pojo.currentWeather.Main?{
        return Gson().fromJson(stmain,com.mahmoudbashir.weathertask_app.pojo.currentWeather.Main::class.java)
    }

    @TypeConverter
    public fun fromWindToString(w: com.mahmoudbashir.weathertask_app.pojo.currentWeather.Wind):String?{
        return Gson().toJson(w)
    }

    @TypeConverter
    public fun fromStringToWind(stwind: String):com.mahmoudbashir.weathertask_app.pojo.currentWeather.Wind?{
        return Gson().fromJson(stwind,com.mahmoudbashir.weathertask_app.pojo.currentWeather.Wind::class.java)
    }

    @TypeConverter
    public fun fromCloudsToString(clouds: Clouds):String?{
        return Gson().toJson(clouds)
    }

    @TypeConverter
    public fun fromStringToClouds(stclouds: String):Clouds?{
        return Gson().fromJson(stclouds,Clouds::class.java)
    }

    @TypeConverter
    public fun fromSysToString(sys: Sys):String?{
        return Gson().toJson(sys)
    }

    @TypeConverter
    public fun fromStringToSys(stsys: String):Sys?{
        return Gson().fromJson(stsys,Sys::class.java)
    }

    @TypeConverter
    public fun fromCurrentSysToString(sys: Current_Sys):String?{
        return Gson().toJson(sys)
    }

    @TypeConverter
    public fun fromStringToCurrentSys(stsys: String):Current_Sys?{
        return Gson().fromJson(stsys,Current_Sys::class.java)
    }

    @TypeConverter
    public fun fromListInfoToString(list: List<InfoList>):String?{
        return Gson().toJson(list)
    }

    @TypeConverter
    public fun fromStringToListInfo(stlistInfo:String):List<InfoList>{
        val listType = object : TypeToken<List<InfoList>>() {}.type
        return Gson().fromJson(stlistInfo,listType)
    }

    @TypeConverter
    public fun fromRainToString(rain: _Rain):String?{
        return Gson().toJson(rain)
    }

    @TypeConverter
    public fun fromStringToRain(strain: String):_Rain?{
        return Gson().fromJson(strain,_Rain::class.java)
    }

    @TypeConverter
    public fun fromSnowToString(snow: _Snow):String?{
        return Gson().toJson(snow)
    }

    @TypeConverter
    public fun fromStringToSnow(stsnow: String):_Snow?{
        return Gson().fromJson(stsnow,_Snow::class.java)
    }


    @TypeConverter
    public fun fromCitiesToString(city:List<Cities>):String?{
        return Gson().toJson(city)
    }

    @TypeConverter
    public fun fromStringCities(stcity:String):List<Cities>{
        val listType = object : TypeToken<List<Cities>>() {}.type
        return Gson().fromJson(stcity,listType)
    }
}