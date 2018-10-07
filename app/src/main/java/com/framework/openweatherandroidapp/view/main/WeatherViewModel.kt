package com.framework.openweatherandroidapp.view.main

import com.framework.openweatherandroidapp.model.WeatherModel
import com.framework.openweatherandroidapp.repository.RepoResponseListener
import com.framework.openweatherandroidapp.repository.WeatherRepository
import com.framework.openweatherandroidapp.utils.execute
import com.framework.openweatherandroidapp.viewmodel.BaseViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : BaseViewModel() {

    fun getWeather(cityName: String, responseListener: RepoResponseListener<WeatherModel>) {
        subscribe(weatherRepository.getWeather(cityName).execute(responseListener))
    }

    fun getCities() = weatherRepository.getCities()

    fun addCity(cityName: String) = weatherRepository.addCity(cityName)
}