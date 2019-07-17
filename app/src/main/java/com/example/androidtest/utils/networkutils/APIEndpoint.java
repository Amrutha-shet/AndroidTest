package com.example.androidtest.utils.networkutils;

import com.example.androidtest.model.APIResponse;
import retrofit2.Call;

import retrofit2.http.GET;

public interface APIEndpoint {
    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<APIResponse> getResponse();
}
