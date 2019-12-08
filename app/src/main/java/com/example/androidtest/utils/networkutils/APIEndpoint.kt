package com.example.androidtest.utils.networkutils

import com.example.androidtest.model.APIResponse
import retrofit2.Call
import retrofit2.Response

import retrofit2.http.GET

interface APIEndpoint {
    @GET("s/2iodh4vg0eortkl/facts.json")
    suspend fun response(): Response<APIResponse>
}
