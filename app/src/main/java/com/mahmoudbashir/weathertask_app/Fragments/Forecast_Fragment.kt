package com.mahmoudbashir.weathertask_app.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mahmoudbashir.weathertask_app.R
import com.mahmoudbashir.weathertask_app.adapters.ForecastAdapter
import com.mahmoudbashir.weathertask_app.databinding.FragmentForecastBinding
import com.mahmoudbashir.weathertask_app.pojo.InfoList
import com.mahmoudbashir.weathertask_app.pojo.forecastWeather.WeatherModel
import com.mahmoudbashir.weathertask_app.ui.MainActivity
import com.mahmoudbashir.weathertask_app.util.Constants
import com.mahmoudbashir.weathertask_app.viewModel.WeatherViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Forecast_Fragment : Fragment() {

    lateinit var forecastBinding:FragmentForecastBinding
    lateinit var viewModel:WeatherViewModel
    lateinit var adapterForecast:ForecastAdapter
    var city_name:String=""
    var path_from:String=""
    lateinit var mlist:ArrayList<InfoList>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        forecastBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_forecast_, container, false)
        //get args data cus we are using nav_graph (navigation component)
        path_from = arguments?.let { Forecast_FragmentArgs.fromBundle(it).pathFrom }.toString()
        city_name = arguments?.let { Forecast_FragmentArgs.fromBundle(it).cityName }.toString()

        viewModel = (activity as MainActivity).weather_viewModel// do initialize for viewModel

        //pressing back to navigate up for previous stack/path
        forecastBinding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        MainScope().launch {
            getForecastWeather(city_name)//get data from api as Forecast weather with giving city name
            delay(1000)
            setUpRecyclerView() //setUp recycler view
        }
        // get stored forecast and check if it was added before or not
        // checking if there is an internet connection or not
        // if no connection then retrieve a stored forecast if this city already stored
        getStoredForecast(city_name)
        Log.d("rrr : ","$city_name")



        return forecastBinding.root
    }
    // insert forecast data to local Room db
    private fun insertForecastDataToRoom(weather_Model: WeatherModel){
        forecastBinding.addForecastBtn.setOnClickListener {
            val check = viewModel.insertForecastWeather(weather_Model).isActive.and(isAdded)
            if (check){
                Toast.makeText(context,"Added Successfully",Toast.LENGTH_SHORT).show()
                forecastBinding.isAdded = true
            }
        }
    }

    private fun setUpRecyclerView() {
        adapterForecast = ForecastAdapter(mlist,requireContext())
        forecastBinding.recForecast.apply {
            setHasFixedSize(true)
            adapter = adapterForecast
        }
    }
    private fun setListToViews(list:List<InfoList>){
        for (elem in list){
            // here i use a split for date that i have got it from api why did it?
            // cus it retrieving 6 or more value for one day
            // so i see its a way for set up one item for views
            val dt_txt = elem.dt_txt.split(" ").toTypedArray()
            val checking = dt_txt[1] == "12:00:00"
            if (checking){
                mlist.add(elem)
            }
        }
    }
    private fun getStoredForecast(city_name: String){
        val networkStatus = viewModel.hasInternetConnection()
        mlist = ArrayList()
        forecastBinding.isLoading = true
        forecastBinding.isAdded = false
        viewModel.getStoredForecastFromRoom().observe(viewLifecycleOwner, Observer {
            it->
            //Log.d("added_forecast:"," list : $it")
            if (it != null ){
                for (elem in it){
                    val city = elem.city.name.toLowerCase()
                  if (city_name == city){
                      val list:List<InfoList>? =elem.list
                      if (list != null && !networkStatus) {
                          setListToViews(list)
                      }
                      Log.d("check_forecast:"," city : $city , checking $networkStatus")
                     forecastBinding.isLoading = false
                    forecastBinding.isAdded = true
                  }
                }
            }
        })
    }

    private suspend fun getForecastWeather(city_name:String){
        mlist=ArrayList()
        forecastBinding.isLoading = true
        viewModel.getWeatherForecast(city_name, Constants.API_KEY, Constants.BASE_Units).enqueue(
            object : Callback<WeatherModel> {
                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
                    if (response.code() == 200) {
                        Log.d("added_forecast:"," city : ${response.body()?.city?.name}")
                        val list:List<InfoList>? = response.body()?.list
                        response.body()?.let { insertForecastDataToRoom(it) }
                        if (list != null ) {
                           setListToViews(list)
                           forecastBinding.isLoading = false
                        }
                    }
                }
                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    t.message
                }
            })
    }
}