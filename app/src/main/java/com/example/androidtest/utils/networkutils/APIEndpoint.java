package com.example.androidtest.utils.networkutils;

import com.example.androidtest.model.APIResponse;
import retrofit2.Call;

import retrofit2.http.GET;

public interface APIEndpoint {

    @GET()
    Call<APIResponse> getResponse();
}
