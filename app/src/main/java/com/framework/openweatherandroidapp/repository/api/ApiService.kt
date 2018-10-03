package com.framework.openweatherandroidapp.repository.api

import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    fun getUsers(): Observable<List<String>>
}