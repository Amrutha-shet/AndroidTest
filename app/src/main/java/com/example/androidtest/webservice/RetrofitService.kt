package com.example.androidtest.webservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun <S> cteateService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

}