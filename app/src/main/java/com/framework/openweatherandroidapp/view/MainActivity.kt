package com.framework.openweatherandroidapp.view

import android.os.Bundle
import com.framework.openweatherandroidapp.App
import com.framework.openweatherandroidapp.R
import com.framework.openweatherandroidapp.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppBaseActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.instance.getApplicationComponent().inject(this)
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.clearSubscription()
    }
}
