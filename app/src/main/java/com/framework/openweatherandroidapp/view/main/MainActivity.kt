package com.framework.openweatherandroidapp.view.main

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.framework.openweatherandroidapp.App
import com.framework.openweatherandroidapp.R
import com.framework.openweatherandroidapp.model.WeatherModel
import com.framework.openweatherandroidapp.repository.RepoResponseListener
import com.framework.openweatherandroidapp.utils.WeatherUtils
import com.framework.openweatherandroidapp.view.AppBaseActivity
import com.framework.openweatherandroidapp.viewmodel.BaseViewModel
import com.framework.openweatherandroidapp.viewmodel.WeatherViewModelFactory
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppBaseActivity() {
    private val PLACE_AUTOCOMPLETE_REQUEST_CODE = 1

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory
    private lateinit var viewModel: WeatherViewModel

    override fun getViewModel(): BaseViewModel {
        App.instance.getApplicationComponent().inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)
        return viewModel
    }

    override fun injectView(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_search) {
            openSearch()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openSearch() {
        try {
            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this)
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            Log.i("MainActivity", e.localizedMessage)
        } catch (e: GooglePlayServicesNotAvailableException) {
            Log.i("MainActivity", e.localizedMessage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = PlaceAutocomplete.getPlace(this, data)
                    val cityName = place.name.toString()
                    Log.i("MainActivity", "Place: $cityName")

                    search(cityName)
                }
                PlaceAutocomplete.RESULT_ERROR -> {
                    val status = PlaceAutocomplete.getStatus(this, data)
                    Log.i("MainActivity", status.statusMessage)

                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
        }
    }

    private fun search(cityName: String) {
        viewModel.getWeather(cityName, object : RepoResponseListener<WeatherModel> {
            override fun onSuccess(response: WeatherModel) {
                Log.i("MainActivity", response.toString())
                bindData(response)
            }

            override fun onError(error: String?) {
                Log.i("MainActivity", error)
            }
        })
    }

    private fun bindData(weatherModel: WeatherModel) {
        val weather = weatherModel.weather[0]
        val weatherImgId = WeatherUtils.getWeatherImageId(weatherId = weather.id)
        icon.setImageResource(weatherImgId)
        textViewCurrentTemperature.text = WeatherUtils.getTempString(weatherModel.main.temp, WeatherUtils.TEMP_UNIT.Celsius)

        textViewWeatherSummary.text = weather.description.toUpperCase()
        cityNameTxt.text = weatherModel.name

        textViewCloudCoverageValue.text = "Clouds: "+weatherModel.clouds.all+"%"
//        tv_extreme_temp.text = WeatherUtils.getExtremeTempsString(weatherModel.main.tempMax, weatherModel.main.tempMin, WeatherUtils.TEMP_UNIT.Celsius)
        textViewHumidityValue.text = WeatherUtils.getHumidityString(weatherModel.main.humidity)
        textViewWindSpeedValue.text = WeatherUtils.getWindString(weatherModel.wind.speed, WeatherUtils.WIND_UNIT.KMH)
    }
}