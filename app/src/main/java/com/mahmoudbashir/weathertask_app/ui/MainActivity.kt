package com.mahmoudbashir.weathertask_app.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.mahmoudbashir.weathertask_app.R
import com.mahmoudbashir.weathertask_app.WeatherApplication
import com.mahmoudbashir.weathertask_app.di.modules.WeatherModule
import com.mahmoudbashir.weathertask_app.repository.WeatherRepository
import com.mahmoudbashir.weathertask_app.viewModel.ViewModelProviderFactory
import com.mahmoudbashir.weathertask_app.viewModel.WeatherViewModel

class MainActivity : AppCompatActivity() {
    lateinit var weather_viewModel :WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = Color.WHITE
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        injectDagger()
        setUpViewModel()

    }
    private fun injectDagger(){
        WeatherApplication.instance.weather_Comp.inject(this)
    }
    private fun setUpViewModel(){
        val repo = WeatherRepository(WeatherModule(this.application).providesRoomDatabase())
        val viewModelProviderFactory = ViewModelProviderFactory(application,repo)
        weather_viewModel = ViewModelProvider(this,viewModelProviderFactory).get(WeatherViewModel::class.java)
    }
}