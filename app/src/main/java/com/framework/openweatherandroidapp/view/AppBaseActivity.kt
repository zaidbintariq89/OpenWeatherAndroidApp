package com.framework.openweatherandroidapp.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.framework.openweatherandroidapp.viewmodel.BaseViewModel

abstract class AppBaseActivity<T: BaseViewModel>: AppCompatActivity() {

    private var mViewModel: T? = null
    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): T

    abstract fun injectView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(injectView())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
    }

    override fun onStop() {
        super.onStop()
        mViewModel?.clearSubscription()
    }
}