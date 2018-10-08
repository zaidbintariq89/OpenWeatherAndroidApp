package com.framework.openweatherandroidapp.view.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.framework.openweatherandroidapp.App
import com.framework.openweatherandroidapp.R
import com.framework.openweatherandroidapp.location.LocationManager
import com.framework.openweatherandroidapp.model.WeatherModel
import com.framework.openweatherandroidapp.repository.RepoResponseListener
import com.framework.openweatherandroidapp.utils.CommonUtility
import com.framework.openweatherandroidapp.utils.WeatherUtils
import com.framework.openweatherandroidapp.view.AppBaseActivity
import com.framework.openweatherandroidapp.view.addcity.AddCityActivity
import com.framework.openweatherandroidapp.viewmodel.BaseViewModel
import com.framework.openweatherandroidapp.viewmodel.BaseViewModelFactory
import com.google.android.gms.location.LocationListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : AppBaseActivity(), LocationListener {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory
    private lateinit var viewModel: WeatherViewModel

    private lateinit var locationManager: LocationManager

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

        locationManager = LocationManager(this, this)
        if (intent.hasExtra("cityName")) {
            val cityname = intent.extras.getString("cityName")
            search(cityname)
        } else {
            checkGPS()
        }
    }

    private fun checkGPS() {
        if (locationManager.hasLocationPermission() && !locationManager.isConnected) {
            locationManager.connect()
        } else if (locationManager.hasLocationPermission() && locationManager.isConnected) {
            locationManager.getLocationUpdates()
        } else {
            CommonUtility.checkLocationPermissions(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CommonUtility.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.isEmpty()) run {
                openAddCityView()
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) run {
                locationManager.connect()
            } else {
                openAddCityView()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_search) {
            openAddCityView()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAddCityView() {
        val intent = Intent(this, AddCityActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun search(cityName: String) {
        progressBar.visibility = View.VISIBLE
        cardMain.visibility = View.GONE
        viewModel.getWeather(cityName, object : RepoResponseListener<WeatherModel> {
            override fun onSuccess(response: WeatherModel) {
                progressBar.visibility = View.GONE
                cardMain.visibility = View.VISIBLE
                Log.i("MainActivity", response.toString())

                // add city
                viewModel.addCity(cityName)
                // bindData to UI
                bindData(response)
            }

            override fun onError(error: String?) {
                Log.i("MainActivity", error)
                progressBar.visibility = View.GONE
                openAddCityView()
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

        textViewCloudCoverageValue.text = "Clouds: " + weatherModel.clouds.all + "%"
        textViewHumidityValue.text = WeatherUtils.getHumidityString(weatherModel.main.humidity)
        textViewWindSpeedValue.text = WeatherUtils.getWindString(weatherModel.wind.speed, WeatherUtils.WIND_UNIT.KMH)
        textViewExtreamValue.text = WeatherUtils.getExtremeTempsString(weatherModel.main.tempMax, weatherModel.main.tempMin, WeatherUtils.TEMP_UNIT.Celsius)

        // update city model
        viewModel.updateCity(weatherModel.name,weatherImgId,weather.description.toUpperCase())
    }

    private fun parseLocationData(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        val cityName = addresses[0].locality

        search(cityName)
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            parseLocationData(location)
        }
    }
}