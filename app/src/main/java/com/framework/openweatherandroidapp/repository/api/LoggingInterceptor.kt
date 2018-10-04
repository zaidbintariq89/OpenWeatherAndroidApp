package com.framework.openweatherandroidapp.repository.api

import com.framework.openweatherandroidapp.App
import com.framework.openweatherandroidapp.utils.isConnectedToInternet
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {

        if(!App.instance.getApplicationComponent().getApplicationContext().isConnectedToInternet()) {
            throw Exception("no Internet connection")
        }

        val request = chain?.request()

        request?.newBuilder()
                ?.method(request.method(), request.body())
                ?.build()

        val response = chain?.proceed(request!!)
        return response!!
    }
}