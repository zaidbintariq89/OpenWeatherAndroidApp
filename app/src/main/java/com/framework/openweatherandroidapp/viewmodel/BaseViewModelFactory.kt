package com.framework.openweatherandroidapp.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.framework.openweatherandroidapp.repository.WeatherRepository
import com.framework.openweatherandroidapp.view.addcity.AddCityViewModel
import com.framework.openweatherandroidapp.view.main.WeatherViewModel
import javax.inject.Inject


class BaseViewModelFactory @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(weatherRepository) as T
        } else if (modelClass.isAssignableFrom(AddCityViewModel::class.java)) {
            return AddCityViewModel(weatherRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}