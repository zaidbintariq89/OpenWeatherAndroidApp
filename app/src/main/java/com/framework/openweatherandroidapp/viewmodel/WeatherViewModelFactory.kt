package com.framework.openweatherandroidapp.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.framework.openweatherandroidapp.repository.WeatherRepository
import com.framework.openweatherandroidapp.view.main.WeatherViewModel
import javax.inject.Inject


class WeatherViewModelFactory @Inject constructor(val weatherRepository: WeatherRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(weatherRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}