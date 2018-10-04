package com.framework.openweatherandroidapp.viewmodel

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel {

    private val subscriptions = CompositeDisposable()

    fun subscribe(disposable: Disposable) {
        subscriptions.add(disposable)
    }

    fun clearSubscription() {
        subscriptions.clear()
    }

}