package com.example.androidtest.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import retrofit2.Response


open class BaseViewModel(application:Application): AndroidViewModel(application){
    protected fun <T> processResponse(response: Response<T>): Pair<T?, Any?> {
        return try {
            if (response.isSuccessful) {
                Pair(response.body(), null)
            } else {
                Pair(null,response.errorBody())
            }
        } catch (e: Exception) {
            Pair(null, e.message)
        }
    }
}