package com.framework.openweatherandroidapp.repository.api

import com.framework.openweatherandroidapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    fun getCurrentWeather(@Query("q") city: String,
                          @Query("appid") key: String,
                          @Query("unit") unit: String): Single<WeatherModel>
}