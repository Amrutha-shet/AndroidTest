package com.example.androidtest.viewmodels


import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.androidtest.model.APIResponse
import com.example.androidtest.model.Rows
import com.example.androidtest.model.dataModel.DataModelItem
import com.example.androidtest.model.repository.ResponseRepo
import com.example.androidtest.utils.networkutils.APIEndpoint
import com.example.androidtest.webservice.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListViewModel : BaseViewModel() {
    private var newsRepository: ResponseRepo? = null
    private var responseApi: APIEndpoint? = null
    var responseData: APIResponse? = null
    var mutableLiveData: MutableLiveData<APIResponse> = MutableLiveData()
    var recyclerViewList: MutableLiveData<MutableList<DataModelItem>> = MutableLiveData()

    fun init() {
        responseApi = RetrofitService.cteateService(APIEndpoint::class.java)
        newsRepository = ResponseRepo.instance
        getResponse()
    }
    /*
    * Used to call api via retrofit instance
    * */
    private fun getResponse() {
        responseApi?.response?.enqueue(object : Callback<APIResponse> {
            override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                if (response.isSuccessful) {
                    responseData = response.body()
                    if (responseData != null) {
                        mutableLiveData.value = responseData
                    }
                }
            }
            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                mutableLiveData.value = null
            }
        })
    }

    /*
   * Used to get a formatted list of row data
   * @param apiResponse
   * @param rows
   * */
    fun setRowdataList(apiResponse: APIResponse, noDataString: String?) {
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
        recyclerViewList.value = rows

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
