package com.framework.openweatherandroidapp.repository.api

import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()

        request?.newBuilder()
                ?.method(request.method(), request.body())
                ?.build()

        val response = chain?.proceed(request!!)
        return response!!
    }
}