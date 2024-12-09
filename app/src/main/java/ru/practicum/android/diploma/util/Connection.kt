package ru.practicum.android.diploma.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
    return false
}
