package com.framework.openweatherandroidapp.view.main

import android.os.Bundle
import android.util.Log
import com.framework.openweatherandroidapp.App
import com.framework.openweatherandroidapp.R
import com.framework.openweatherandroidapp.model.WeatherModel
import com.framework.openweatherandroidapp.view.AppBaseActivity
import javax.inject.Inject

class MainActivity : AppBaseActivity<MainViewModel>(), MainViewModelNavigation {


    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun injectView(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.setNavigation(this)
    }

    override fun getViewModel(): MainViewModel {
        App.instance.getApplicationComponent().inject(this)
        return mainViewModel
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.fetchWeather("Lahore")
    }

    override fun onGetData(weatherModel: WeatherModel) {
        Log.d("Weather",weatherModel.toString())
    }

    override fun onErrorReturn(error: String?) {
        Log.e("Weather-Error",error)
    }

}
