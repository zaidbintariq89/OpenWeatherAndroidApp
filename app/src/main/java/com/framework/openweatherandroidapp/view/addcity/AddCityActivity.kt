package com.framework.openweatherandroidapp.view.addcity

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.framework.openweatherandroidapp.App
import com.framework.openweatherandroidapp.R
import com.framework.openweatherandroidapp.adapter.CityAdapter
import com.framework.openweatherandroidapp.repository.RepoResponseListener
import com.framework.openweatherandroidapp.repository.db.CityEntity
import com.framework.openweatherandroidapp.utils.showToast
import com.framework.openweatherandroidapp.view.AppBaseActivity
import com.framework.openweatherandroidapp.view.main.MainActivity
import com.framework.openweatherandroidapp.viewmodel.BaseViewModel
import com.framework.openweatherandroidapp.viewmodel.BaseViewModelFactory
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import kotlinx.android.synthetic.main.activity_add_city.*
import kotlinx.android.synthetic.main.content_add_city.*
import javax.inject.Inject

class AddCityActivity : AppBaseActivity() {
    private val PLACE_AUTOCOMPLETE_REQUEST_CODE = 1

    private lateinit var context: Context

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory
    private lateinit var viewModel: AddCityViewModel

    private lateinit var cityAdapter: CityAdapter
    private var cityList: ArrayList<CityEntity> = ArrayList()

    override fun getViewModel(): BaseViewModel {
        App.instance.getApplicationComponent().inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddCityViewModel::class.java)
        return viewModel
    }

    override fun injectView(): Int {
        return R.layout.activity_add_city
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        context = this

        initCityList()
        getAllCities()

        fab.setOnClickListener { view ->
            openSearch()
        }
    }

    private fun initCityList() {
        cityAdapter = CityAdapter(cityList)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = cityAdapter

        cityAdapter.setClickListener(View.OnClickListener{
            val position: Int = it.tag as Int
            openDetail(cityList[position].cityName)
        })
    }

    override fun onBackPressed() {
        openDetail("")
    }

    private fun openDetail(cityName: String) {
        val intent = Intent(context,MainActivity::class.java)
        if(cityName.isNotEmpty()) {
            intent.putExtra("cityName", cityName)
        }
        startActivity(intent)
        finish()
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

                    searchWeather(cityName)
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

    private fun getAllCities() {
        viewModel.getCities(object : RepoResponseListener<List<CityEntity>> {
            override fun onSuccess(response: List<CityEntity>) {
                Log.i("MainActivity", response.toString())
                if (response.isNotEmpty()) {
                    val list = ArrayList(response)
                    cityList.clear()
                    cityList.addAll(list)

                    cityAdapter.notifyDataSetChanged()
                }
            }

            override fun onError(error: String?) {
                Log.i("MainActivity", error)
            }
        })
    }

    private fun searchWeather(city: String) {
        viewModel.addCity(city)
        openDetail(city)
    }
}
