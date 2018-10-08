package com.framework.openweatherandroidapp.repository

import com.framework.openweatherandroidapp.App
import com.framework.openweatherandroidapp.R
import com.framework.openweatherandroidapp.model.WeatherModel
import com.framework.openweatherandroidapp.repository.api.ApiService
import com.framework.openweatherandroidapp.repository.db.CityEntity
import com.framework.openweatherandroidapp.repository.db.RoomDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
        private val apiService: ApiService,
        private val roomDataSource: RoomDataSource) : WeatherRepository {

    override fun getWeather(cityName: String): Single<WeatherModel> {
        val appKey = App.instance.getApplicationComponent().getApplicationContext().getString(R.string.weather_api)
        return apiService.getCurrentWeather(cityName, appKey, "metric")
    }

    override fun getCities(): Flowable<List<CityEntity>> {
        return roomDataSource.weatherSearchCityDao().getAllCities()
    }

    override fun addCity(cityName: String) {
        Completable.fromCallable { roomDataSource.weatherSearchCityDao().insertCity(CityEntity(cityName = cityName,icon = 0,summary = "")) }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun updateCity(cityName: String, icon: Int, summary: String) {
        Completable.fromCallable { roomDataSource.weatherSearchCityDao().updatedCity(cityName = cityName,icon = icon,summary = summary) }.subscribeOn(Schedulers.io()).subscribe()
    }
}