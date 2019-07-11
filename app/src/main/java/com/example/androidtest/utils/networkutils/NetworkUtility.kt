package com.example.androidtest.utils.networkutils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Utility method to check is network available
 *
 * @param context:Context
 * @return:Boolean true if network is available else returns false
 *
 */
object NetworkUtility {
    fun isNetworkAvailable(context: Context): Boolean {
        val conn: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = conn.activeNetworkInfo
        return if (networkInfo != null)
            networkInfo.isConnected
        else
            false
    }
}