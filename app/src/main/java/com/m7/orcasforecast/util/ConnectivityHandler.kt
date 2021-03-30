package com.m7.orcasforecast.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityHandler @Inject constructor(@ApplicationContext val context: Context) {

    fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCaps = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork ?: return false
        ) ?: return false

        return when {
            networkCaps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCaps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCaps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

            else -> false
        }
    }
}