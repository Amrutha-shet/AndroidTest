package com.example.androidtest.webservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import javax.xml.datatype.DatatypeConstants.SECONDS
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object RetrofitService {
    val BASE_URL = "https://dl.dropboxusercontent.com/"

    var client = OkHttpClient.Builder()
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS).build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL).client(client)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()


    fun <S> cteateService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

}