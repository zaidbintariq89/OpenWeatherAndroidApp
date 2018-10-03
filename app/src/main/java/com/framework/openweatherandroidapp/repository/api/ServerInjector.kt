package com.framework.openweatherandroidapp.repository.api

import com.framework.openweatherandroidapp.BuildConfig
import com.framework.openweatherandroidapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ServerInjector {

    companion object {
        fun getOkHttpClient(): OkHttpClient {
            val logger = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG)
                logger.level = HttpLoggingInterceptor.Level.BODY
            else
                logger.level = HttpLoggingInterceptor.Level.NONE

            val loggingInterceptor = LoggingInterceptor()

            return OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .addNetworkInterceptor(loggingInterceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build()
        }

        fun provideRetrofit(): Retrofit {
            val httpClient = getOkHttpClient()
            val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()

            return retrofit
        }

        fun provideApiService(): ApiService {
            return provideRetrofit()
                    .create(ApiService::class.java)
        }
    }
}