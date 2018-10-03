package com.framework.openweatherandroidapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.framework.openweatherandroidapp.repository.RepoResponseListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Context.isConnectedToInternet(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = cm.activeNetworkInfo

    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}

fun Context.showToast(message: String, length: Int) {
    Toast.makeText(this, message, length)
            .show()
}

fun <T> Observable<T>.execute(responseListener: RepoResponseListener<T>): Disposable {

    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseListener.onSuccess(it)
            }, {
                responseListener.onError(it.message)
            })
}