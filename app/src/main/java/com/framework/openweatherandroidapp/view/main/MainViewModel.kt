package com.framework.openweatherandroidapp.view.main

import com.framework.openweatherandroidapp.model.WeatherModel
import com.framework.openweatherandroidapp.repository.RepoResponseListener
import com.framework.openweatherandroidapp.repository.WeatherRepository
import com.framework.openweatherandroidapp.viewmodel.BaseViewModel

class MainViewModel(private val weatherRepository: WeatherRepository) : BaseViewModel() {

    private var mainViewModelNavigation: MainViewModelNavigation? = null

    fun fetchWeather(cityName: String) {
        subscribe(weatherRepository.fetchCurrentWeather(cityName,
                object : RepoResponseListener<WeatherModel> {

                    override fun onSuccess(response: WeatherModel) {
                        mainViewModelNavigation?.onGetData(response)
                    }

                    override fun onError(error: String?) {
                        mainViewModelNavigation?.onErrorReturn(error)
                    }
                }))
    }

    fun setNavigation(navigation: MainViewModelNavigation) {
        mainViewModelNavigation = navigation
    }
}