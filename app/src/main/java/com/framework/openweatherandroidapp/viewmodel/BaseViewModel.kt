package com.framework.openweatherandroidapp.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {

    private val subscriptions = CompositeDisposable()

    fun subscribe(disposable: Disposable) {
        subscriptions.add(disposable)
    }

    fun clearSubscription() {
        subscriptions.clear()
    }

}