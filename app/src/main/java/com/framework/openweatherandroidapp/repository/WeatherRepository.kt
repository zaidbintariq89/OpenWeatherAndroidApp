package com.framework.openweatherandroidapp.repository

import com.framework.openweatherandroidapp.App
import com.framework.openweatherandroidapp.R
import com.framework.openweatherandroidapp.model.WeatherModel
import com.framework.openweatherandroidapp.repository.api.ApiService
import com.framework.openweatherandroidapp.utils.execute
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService) {

    fun fetchCurrentWeather(cityName: String, repoResponseListener: RepoResponseListener<WeatherModel>): Disposable {
        val appKey = App.instance.getApplicationComponent().getApplicationContext().getString(R.string.api_key)
        val service = apiService.getCurrentWeather(cityName, appKey, "metric")
        return service.execute(repoResponseListener)
    }
}