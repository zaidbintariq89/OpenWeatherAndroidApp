package com.framework.openweatherandroidapp

import android.content.Context
import com.framework.openweatherandroidapp.repository.api.ApiService
import com.framework.openweatherandroidapp.utils.SharedPrefsUtility
import com.framework.openweatherandroidapp.view.main.MainActivity
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun getApiService(): ApiService
    fun getApplicationContext(): Context
    fun getRetrofit(): Retrofit
    fun getSharedPrefUtilty(): SharedPrefsUtility
}