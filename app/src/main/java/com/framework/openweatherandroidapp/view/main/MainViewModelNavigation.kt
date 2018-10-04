package com.framework.openweatherandroidapp.view.main

import com.framework.openweatherandroidapp.model.WeatherModel

interface MainViewModelNavigation {

    fun onGetData(weatherModel: WeatherModel)
    fun onErrorReturn(error: String?)
}