package com.framework.openweatherandroidapp

import android.app.Application

class App : Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()

        if (BuildConfig.DEBUG) {
            // Maybe TimberPlant etc.
        }
    }

    private fun setup() {
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun getApplicationComponent(): AppComponent {
        return component
    }

    companion object {
        lateinit var instance: App private set
    }
}