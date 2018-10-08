package com.framework.openweatherandroidapp

import android.content.Context
import com.framework.openweatherandroidapp.view.addcity.AddCityActivity
import com.framework.openweatherandroidapp.view.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(activity: AddCityActivity)
    fun getApplicationContext(): Context
//    fun getApiService(): ApiService
//    fun getRetrofit(): Retrofit
//    fun getSharedPrefUtilty(): SharedPrefsUtility
}