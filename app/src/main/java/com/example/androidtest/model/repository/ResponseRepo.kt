package com.example.androidtest.model.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.androidtest.model.APIResponse
import com.example.androidtest.utils.networkutils.APIEndpoint
import com.example.androidtest.webservice.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ResponseRepo {

    companion object {

        private var newsRepository: ResponseRepo? = null

        val instance: ResponseRepo
            get() {
                if (newsRepository == null) {
                    newsRepository = ResponseRepo()
                }
                return newsRepository as ResponseRepo
            }
    }

    private val responseApi: APIEndpoint



    init {
        responseApi = RetrofitService.cteateService(APIEndpoint::class.java)
    }

    fun getResponse(): MutableLiveData<APIResponse> {
        val responseData = MutableLiveData<APIResponse>()
        responseApi.getResponse().enqueue(object : Callback<APIResponse> {
            override fun onResponse(
                call: Call<APIResponse>,
                response: Response<APIResponse>
            ) {
                if (response.isSuccessful()) {
                    responseData.setValue(response.body())
                    Log.d("Response",response.body().toString())
                }
            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                responseData.setValue(null)
            }
        })
        return responseData
    }


}