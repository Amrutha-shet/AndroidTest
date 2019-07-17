package com.example.androidtest.model

import com.google.gson.annotations.SerializedName

class APIResponse {
    @SerializedName("title")
    val title: String? = null

    @SerializedName("rows")
    val rows: List<Rows>? = null
}