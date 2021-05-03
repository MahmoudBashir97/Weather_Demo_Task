package com.mahmoudbashir.weathertask_app.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudbashir.weathertask_app.WeatherApplication
import com.mahmoudbashir.weathertask_app.pojo.cities_list.CitiesRoot
import com.mahmoudbashir.weathertask_app.pojo.currentWeather.CurrentWeather_Model
import com.mahmoudbashir.weathertask_app.pojo.forecastWeather.WeatherModel
import com.mahmoudbashir.weathertask_app.repository.WeatherRepository
import com.mahmoudbashir.weathertask_app.util.Resource
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class WeatherViewModel (app:Application,private val repo:WeatherRepository):AndroidViewModel(app){

    var citiesMutable:MutableLiveData<CitiesRoot> = MutableLiveData()

    suspend fun getWeatherForecast(q:String,appKey:String,units:String):Call<WeatherModel>{
        return repo.getForeCast(q,appKey,units)
    }

    suspend fun getCurrentWeather(lat:Double,lng:Double,appKey:String,units:String):Call<CurrentWeather_Model>{
        return repo.getCurrentWeather(lat,lng, appKey,units)
    }


    fun insertCurrentWeather(currentweatherModel: CurrentWeather_Model) = viewModelScope.launch {
        repo.insertCurrentWeather(currentweatherModel)
    }

    fun insertForecastWeather(weatherModel: WeatherModel) = viewModelScope.launch {
        repo.insertForecastWeather(weatherModel)
    }

    fun getStoredWeatherFromRoom(): LiveData<CurrentWeather_Model> = repo.getStoredWeatherFromRoom()
    fun getStoredForecastFromRoom(): LiveData<List<WeatherModel>> = repo.getStoredForecastFromRoom()
    fun deleteCityForecast(weatherModel: WeatherModel)=viewModelScope.launch {
        repo.deleteCityForecast(weatherModel)
    }
   /* suspend fun getCities():Call<CitiesRoot>{
        return repo.getCities()
    }*/





    fun hasInternetConnection():Boolean{
        val connectivityManager = getApplication<WeatherApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}