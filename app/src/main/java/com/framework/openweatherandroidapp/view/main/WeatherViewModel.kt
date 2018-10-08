package com.framework.openweatherandroidapp.view.main

import com.framework.openweatherandroidapp.App
import com.framework.openweatherandroidapp.R
import com.framework.openweatherandroidapp.model.WeatherModel
import com.framework.openweatherandroidapp.repository.RepoResponseListener
import com.framework.openweatherandroidapp.repository.WeatherRepository
import com.framework.openweatherandroidapp.utils.execute
import com.framework.openweatherandroidapp.viewmodel.BaseViewModel
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : BaseViewModel() {

    fun getWeather(cityName: String, responseListener: RepoResponseListener<WeatherModel>) {
        val appKey = App.instance.getApplicationComponent().getApplicationContext().getString(R.string.weather_api)
        subscribe(weatherRepository.getWeather(cityName,appKey).execute(responseListener))
    }

    fun addCity(cityName: String) = weatherRepository.addCity(cityName)

    fun updateCity(cityName: String, icon: Int, summary: String) = weatherRepository.updateCity(cityName,icon,summary)
}