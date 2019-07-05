package com.example.androidtest.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.androidtest.model.APIResponse
import com.example.androidtest.model.repository.ResponseRepo
import com.example.androidtest.utils.networkutils.APIEndpoint
import com.example.androidtest.webservice.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException


class ListViewModel : BaseViewModel() {
    var mutableLiveData: MutableLiveData<APIResponse> = MutableLiveData()
    private var newsRepository: ResponseRepo? = null
    private var responseApi: APIEndpoint ? = null
    var responseData : APIResponse ? = null
    var title: MutableLiveData<String> = MutableLiveData()

    fun init() {

        responseApi = RetrofitService.cteateService(APIEndpoint::class.java)
        newsRepository = ResponseRepo.instance
        getResponse()
    }


    fun getResponse() {


        responseApi?.getResponse()?.enqueue(object : Callback<APIResponse> {
            override fun onResponse(
                call: Call<APIResponse>,
                response: Response<APIResponse>
            ) {
                Log.d("Response", response.body().toString())
                if (response.isSuccessful()) {
                    responseData = response.body()

                    Log.d("Response Success", response.body().toString())
                    if(responseData != null) {
                        mutableLiveData.value = responseData
                    }

                }

            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {

            }
        })
    }


}
