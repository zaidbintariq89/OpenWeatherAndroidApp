package com.framework.openweatherandroidapp.view.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.framework.openweatherandroidapp.App
import com.framework.openweatherandroidapp.R
import com.framework.openweatherandroidapp.model.WeatherModel
import com.framework.openweatherandroidapp.utils.WeatherUtils
import com.framework.openweatherandroidapp.utils.isConnectedToInternet
import com.framework.openweatherandroidapp.utils.showToast
import com.framework.openweatherandroidapp.view.AppBaseActivity
import kotlinx.android.synthetic.main.activity_main.*
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
        handleIntent(intent)

//        setSupportActionBar(toolbar)
    }

    override fun getViewModel(): MainViewModel {
        App.instance.getApplicationComponent().inject(this)
        return mainViewModel
    }

    override fun onResume() {
        super.onResume()
        fetchWeather("Lahore")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (query.isNotEmpty()) {
                fetchWeather(query)
            }
        }
    }

    private fun fetchWeather(query: String) {
        if (isConnectedToInternet()) {
            mainViewModel.fetchWeather(query)
        } else {
            showToast("No Internet Connection",Toast.LENGTH_LONG)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

    override fun onGetData(weatherModel: WeatherModel) {
        Log.d("Weather", weatherModel.toString())

        val weather = weatherModel.weather[0]
        val weatherImgId = WeatherUtils.getWeatherImageId(weatherId = weather.id)
        img_weather.setImageResource(weatherImgId)

        tv_city_name.text = weatherModel.name
        tv_extreme_temp.text = WeatherUtils.getExtremeTempsString(weatherModel.main.tempMax, weatherModel.main.tempMin, WeatherUtils.TEMP_UNIT.Celsius)
        tv_cur_temp.text = WeatherUtils.getTempString(weatherModel.main.temp, WeatherUtils.TEMP_UNIT.Celsius)
        tv_humidity.text = WeatherUtils.getHumidityString(weatherModel.main.humidity)
        tv_wind_speed.text = WeatherUtils.getWindString(weatherModel.wind.speed, WeatherUtils.WIND_UNIT.KMH)
        tv_description.text = weather.description

    }

    override fun onErrorReturn(error: String?) {

        if (!isConnectedToInternet()) {
            showToast("No Internet Connection",Toast.LENGTH_LONG)
        } else {
            showToast(error!!,Toast.LENGTH_LONG)
        }
    }

}
