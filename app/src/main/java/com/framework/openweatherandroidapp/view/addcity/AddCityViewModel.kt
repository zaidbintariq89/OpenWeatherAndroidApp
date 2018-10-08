package com.framework.openweatherandroidapp.view.addcity

import com.framework.openweatherandroidapp.repository.RepoResponseListener
import com.framework.openweatherandroidapp.repository.WeatherRepository
import com.framework.openweatherandroidapp.repository.db.CityEntity
import com.framework.openweatherandroidapp.utils.execute
import com.framework.openweatherandroidapp.viewmodel.BaseViewModel
import javax.inject.Inject

class AddCityViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : BaseViewModel() {

    fun getCities(responseListener: RepoResponseListener<List<CityEntity>>) {
        subscribe(weatherRepository.getCities().execute(responseListener))
    }

    fun addCity(cityName: String) = weatherRepository.addCity(cityName)
}