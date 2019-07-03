package com.example.androidtest.viewmodels

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidtest.model.APIResponse
import com.example.androidtest.model.repository.ResponseRepo


class ListViewModel : ViewModel() {
    private var mutableLiveData: MutableLiveData<APIResponse>? = null
    private var newsRepository: ResponseRepo? = null

    fun init() {
        if (mutableLiveData != null) {
            return
        }
        newsRepository = ResponseRepo.instance

        mutableLiveData = if(newsRepository?.getResponse() != null) {newsRepository?.getResponse()} else return

    }

    fun getAPIResponse(): LiveData<APIResponse>? {
        return mutableLiveData
    }
}
