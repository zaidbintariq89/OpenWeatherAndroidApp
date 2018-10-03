package com.framework.openweatherandroidapp.repository

interface RepoResponseListener<T> {

    fun onSuccess(response : T)
    fun onError(error: String?)
}