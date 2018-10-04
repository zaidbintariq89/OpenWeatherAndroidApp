package com.framework.openweatherandroidapp.viewmodel

import com.framework.openweatherandroidapp.model.WeatherModel
import com.framework.openweatherandroidapp.repository.RepoResponseListener
import com.framework.openweatherandroidapp.repository.WeatherRepository
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val weatherRepository: WeatherRepository) {

    private val subscriptions = CompositeDisposable()

    fun fetchWeather(cityName: String) {
        val disposable = weatherRepository.fetchCurrentWeather(cityName,
                object : RepoResponseListener<WeatherModel> {

                    override fun onSuccess(response: WeatherModel) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onError(error: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
    }

    fun clearSubscription() {
        subscriptions.clear()
    }
}