package com.framework.openweatherandroidapp

import android.app.Application
import android.content.Context
import com.framework.openweatherandroidapp.repository.WeatherRepository
import com.framework.openweatherandroidapp.repository.WeatherRepositoryImpl
import com.framework.openweatherandroidapp.repository.api.ApiService
import com.framework.openweatherandroidapp.repository.api.ServerInjector
import com.framework.openweatherandroidapp.utils.SharedPrefsUtility
import com.framework.openweatherandroidapp.repository.db.RoomDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit = ServerInjector.provideRetrofit()

    @Provides
    @Singleton
    fun providesUserApi(): ApiService = ServerInjector.provideApiService()

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPrefsUtility =
            SharedPrefsUtility(application.getSharedPreferences("PrefName", Context.MODE_PRIVATE))

    @Provides
    @Singleton
    fun provideWeatherRepository(): WeatherRepository {
        return WeatherRepositoryImpl(providesUserApi(),
                provideRoomCurrencyDataSource(application))
    }

    @Provides
    @Singleton
    fun provideRoomCurrencyDataSource(context: Context) =
            RoomDataSource.getInstance(context)
}