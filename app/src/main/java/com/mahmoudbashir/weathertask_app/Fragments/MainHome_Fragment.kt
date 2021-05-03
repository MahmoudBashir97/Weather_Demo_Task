package com.mahmoudbashir.weathertask_app.Fragments

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.mahmoudbashir.weathertask_app.R
import com.mahmoudbashir.weathertask_app.adapters.CitiesListOnClickInterface
import com.mahmoudbashir.weathertask_app.adapters.cities_forecast_adapter
import com.mahmoudbashir.weathertask_app.databinding.FragmentMainHomeBinding
import com.mahmoudbashir.weathertask_app.local.SharedPreferenceM
import com.mahmoudbashir.weathertask_app.pojo.currentWeather.CurrentWeather_Model
import com.mahmoudbashir.weathertask_app.pojo.forecastWeather.WeatherModel
import com.mahmoudbashir.weathertask_app.ui.MainActivity
import com.mahmoudbashir.weathertask_app.util.Constants
import com.mahmoudbashir.weathertask_app.viewModel.WeatherViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MainHome_Fragment : Fragment() ,CitiesListOnClickInterface{

    lateinit var HomeBinding:FragmentMainHomeBinding
    lateinit var weather_viewModel:WeatherViewModel
    lateinit var lm:LocationManager
    lateinit var citiesForecastAdapter: cities_forecast_adapter
    lateinit var mlist:ArrayList<WeatherModel>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private  final val REQUEST_CODE_LOCATION_PERMISSION = 1
    private val REQUIRED_SDK_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val REQUEST_CODE_ASK_PERMISSIONS = 1
    var lat: Double? = null
    var lng:Double? = null
    var gpsEnabled=false
    var currentCity=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        HomeBinding =  DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_home_,
            container,
            false
        )
        checkPermissions()// as location permission for using it by getting lat & lon
        checkGPSEnabled()
        weather_viewModel = (activity as MainActivity).weather_viewModel // do initialize for viewModel



            val networkStatus = weather_viewModel.hasInternetConnection()
            if (networkStatus){
                GlobalScope.launch(Dispatchers.IO){
                    delay(500)
                   getCurrentWeather()
                }
            }else{
                val c = SharedPreferenceM.getInastance(context).cityName
                getStoredForecast(c)
        }
        addNewCity()// enable add btn for browsing and adding new city as forecast weather
        setDataToViws() // set data to views
        swapRightToDelete()// swapping right to delete from cities forecast

        return HomeBinding.root
    }

    private fun swapRightToDelete(){
        val mIth = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val data = mlist[position]
                weather_viewModel.deleteCityForecast(data)
                citiesForecastAdapter.notifyDataSetChanged()
                Snackbar.make(
                    HomeBinding.root,
                    "Successfully deleted!!",
                    Snackbar.LENGTH_SHORT
                ).apply {
                    show()
                }
            }
        }
        ItemTouchHelper(mIth).apply {
            attachToRecyclerView(HomeBinding.recCities)
        }
    }
    /*
    Checking GPS enabled or not
    */
    private fun checkGPSEnabled() {
        HomeBinding.isEnabled = false
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        lm = (context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager)

        gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!gpsEnabled){
            Log.d("gpsStatus","GPS not Enabled")
            HomeBinding.isEnabled = false
            HomeBinding.txtCurrentCity.text = "London"
            lat = 51.509865
            lng = -0.118092
        }else{
            HomeBinding.isEnabled = true
            getCurrentLocation()
        }
    }



    private fun setDataToViws(){
        weather_viewModel.getStoredWeatherFromRoom().observe(viewLifecycleOwner, Observer {
            if (it != null || gpsEnabled) {
                val temp = it.main.temp
                val statusName = it.weather[0].main.toString()
                currentCity = it.name
                HomeBinding.txtWeatherDegree.text = temp.toInt().toString()
                HomeBinding.txtWeatherStatusName.text = statusName
                HomeBinding.txtCurrentCity.text = currentCity
            }
        })
        HomeBinding.toMyForecastBtn.setOnClickListener {
            if (!gpsEnabled){
                currentCity = "london"
            }
            findNavController().navigate(MainHome_FragmentDirections.actionMainHomeFragmentToForecastFragment("mainHome",currentCity))
        }
        getForecastWeather(currentCity)
    }

    private suspend fun getCurrentWeather(){
        delay(500)
        if (lat !=null){
        weather_viewModel.getCurrentWeather(lat!!,lng!!, Constants.API_KEY, Constants.BASE_Units)
            .enqueue(object : Callback<CurrentWeather_Model> {
                override fun onResponse(
                    call: Call<CurrentWeather_Model>,
                    response: Response<CurrentWeather_Model>
                ) {
                     if (response.code() == 200) {
                        response.body()?.let {
                            if (!gpsEnabled){
                                val temp = it.main.temp
                                val statusName = it.weather[0].main.toString()
                                currentCity = it.name
                                HomeBinding.txtWeatherDegree.text = temp.toInt().toString()
                                HomeBinding.txtWeatherStatusName.text = statusName
                                HomeBinding.txtCurrentCity.text = currentCity
                            }
                            weather_viewModel.insertCurrentWeather(it)
                            val check = weather_viewModel.insertCurrentWeather(it).isActive.and(
                                isAdded
                            )
                            if (check) {
                                Log.d("homeHere :", "added successfully")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<CurrentWeather_Model>, t: Throwable) {
                    t.message
                }
            })
        }
    }
    private fun getForecastWeather(city_name:String){
        var city = if (gpsEnabled){
            city_name
        }else{
            "london"
        }
        GlobalScope.launch {
            weather_viewModel.getWeatherForecast(city, Constants.API_KEY, Constants.BASE_Units).enqueue(
                object : Callback<WeatherModel> {
                    override fun onResponse(
                        call: Call<WeatherModel>,
                        response: Response<WeatherModel>
                    ) {
                        if (response.code() == 200) {
                            SharedPreferenceM.getInastance(context).save_city_name(city)
                            getStoredForecast(city)
                        }
                    }

                    override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                        t.message
                    }
                })
        }

    }


    private fun getStoredForecast(city_name: String){
        mlist= ArrayList()
        weather_viewModel.getStoredForecastFromRoom().observe(viewLifecycleOwner, Observer {
                it->
            //Log.d("added_forecast:"," list : $it")
            if (it != null){
                mlist.clear()
                for (elem in it){
                    mlist.add(elem)
                    val city = elem.city.name.toLowerCase()
                    if (city_name == city){
                        mlist[0] = elem
                    }
                }
                setUpRecyclerView()
            }
        })
    }

    private fun setUpRecyclerView(){
        citiesForecastAdapter = cities_forecast_adapter(mlist,this)
        HomeBinding.recCities.apply {
            setHasFixedSize(true)
            adapter = citiesForecastAdapter
        }
    }

    private fun addNewCity(){
        HomeBinding.addCityBtn.setOnClickListener {
            findNavController().navigate(MainHome_FragmentDirections.actionMainHomeFragmentToSearchFragment())
        }
    }

    private fun getCurrentLocation() {
        Log.d("trackingLoc :", "work")
        val locationReq:LocationRequest = LocationRequest()
        locationReq.setInterval(10000)
        locationReq.setFastestInterval(3000)
        locationReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        Log.d("trackingLoc :", "work2")
        LocationServices.getFusedLocationProviderClient(requireContext())
            .requestLocationUpdates(locationReq, object : LocationCallback() {
                override fun onLocationResult(result: LocationResult?) {
                    super.onLocationResult(result)
                    LocationServices.getFusedLocationProviderClient(requireContext())
                        .removeLocationUpdates(this)
                    if (result != null && result.locations.size > 0) {
                        Log.d("trackingLoc :", "work3")
                        val latestLocationIndex = result.locations.size - 1
                        lat = result.locations[latestLocationIndex].latitude
                        lng = result.locations[latestLocationIndex].longitude
                    }
                }
            }, Looper.getMainLooper())
    }

    protected fun checkPermissions() {
        val missingPermissions: MutableList<String> = ArrayList()
        // check all required dynamic permissions
        for (permission in REQUIRED_SDK_PERMISSIONS) {
            val result = ContextCompat.checkSelfPermission(requireContext(), permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission)
            }
        }
        if (missingPermissions.isNotEmpty()) {
            // request all missing permissions
            val permissions = missingPermissions
                .toTypedArray()
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissions,
                REQUEST_CODE_ASK_PERMISSIONS
            )
        } else {
            val grantResults =
                IntArray(REQUIRED_SDK_PERMISSIONS.size)
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED)
            onRequestPermissionsResult(
                REQUEST_CODE_LOCATION_PERMISSION,
                REQUIRED_SDK_PERMISSIONS,
                grantResults
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION_PERMISSION -> {
                var index = permissions.size - 1
                while (index >= 0) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(
                            context, "Required permission '" + permissions[index]
                                    + "' not granted, exiting", Toast.LENGTH_LONG
                        ).show()
                        //finish()
                        return
                    }else{
                        checkGPSEnabled()
                    }
                    --index
                }
            }
        }
    }

    override fun CitiesOnClick(city_name: String, position: Int) {
        findNavController().navigate(MainHome_FragmentDirections.actionMainHomeFragmentToForecastFragment("MainHome",city_name))
    }
}