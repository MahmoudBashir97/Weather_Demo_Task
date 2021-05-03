@file:Suppress("UNCHECKED_CAST")

package com.mahmoudbashir.weathertask_app.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudbashir.weathertask_app.repository.WeatherRepository
import javax.inject.Inject

class ViewModelProviderFactory @Inject constructor(
    private val app: Application,
    private val repos: WeatherRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(app, repos) as T
    }
}
