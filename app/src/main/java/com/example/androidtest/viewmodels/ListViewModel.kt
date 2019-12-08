package com.example.androidtest.viewmodels


import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidtest.R
import com.example.androidtest.model.APIResponse
import com.example.androidtest.model.Rows
import com.example.androidtest.model.dataModel.DataModelItem
import com.example.androidtest.utils.networkutils.APIEndpoint
import com.example.androidtest.webservice.RetrofitService
import kotlinx.coroutines.launch


class ListViewModel(application: Application) : BaseViewModel(application) {
    private var responseApi: APIEndpoint = RetrofitService.cteateService(APIEndpoint::class.java)
    private var errorMessege: MutableLiveData<String> = MutableLiveData()
    private var response: MutableLiveData<MutableList<DataModelItem>> = MutableLiveData()
    private var title: MutableLiveData<String> = MutableLiveData()

    fun init() {
        viewModelScope.launch {
            getResponse()
        }
    }

    fun getResponseList(): LiveData<MutableList<DataModelItem>> = response
    fun getErrorLiveData(): LiveData<String> = errorMessege
    fun getTitle(): LiveData<String> = title
    /*
    * Used to call api via retrofit instance using coroutine
    * */
    private suspend fun getResponse() {
        val pair = processResponse(responseApi.response())
        if (pair.first != null) {
            title.value = (pair.first as APIResponse).title
            setRowDataList(
                pair.first as APIResponse, getApplication<Application>()
                    .applicationContext.getString(R.string.content_not_available)
            )
        } else {
            errorMessege.value = pair.second as String
        }
    }

    /*
   * Used to get a formatted list of row data
   * @param apiResponse
   * @param rows
   * */
    fun setRowDataList(apiResponse: APIResponse, noDataString: String?) {
        val rows: MutableList<DataModelItem>? = ArrayList()
        when {
            apiResponse.rows != null && apiResponse.rows.isNotEmpty() ->
                loop@ apiResponse.rows.forEach { row ->
                    val title = getTitle(row)
                    val description = getDescription(row, noDataString)
                    val imageURL = getImageURL(row)
                    when {
                        title == null && description == null && imageURL == null -> return@forEach
                        else -> rows?.add(DataModelItem(title, description, imageURL))
                    }
                }
        }
        response.value = rows

    }

    private fun getImageURL(row: Rows): String? {
        when {
            row.imageHref != null && !TextUtils.isEmpty(row.imageHref) -> return row.imageHref
        }
        return null
    }

    private fun getDescription(row: Rows, noDataString: String?): String? {
        when {
            row.description != null && !TextUtils.isEmpty(row.description) ->
                return row.description
            row.description == null && row.title != null && row.imageHref != null ->
                return noDataString
        }
        return null
    }

    private fun getTitle(row: Rows): String? {
        when {
            row.title != null && !TextUtils.isEmpty(row.title) -> return row.title
        }
        return null
    }
}
