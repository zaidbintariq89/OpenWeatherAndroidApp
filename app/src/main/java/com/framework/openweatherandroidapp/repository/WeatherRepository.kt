package com.framework.openweatherandroidapp.repository

import com.framework.openweatherandroidapp.model.WeatherModel
import com.framework.openweatherandroidapp.repository.db.CityEntity
import io.reactivex.Flowable
import io.reactivex.Single


interface WeatherRepository {

    fun getCities(): Flowable<List<CityEntity>>

    fun getWeather(cityName: String,appKey: String): Single<WeatherModel>

    fun addCity(cityName: String)

    fun updateCity(cityName: String, icon: Int, summary: String)
}