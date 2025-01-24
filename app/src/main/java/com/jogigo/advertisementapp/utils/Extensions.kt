package com.jogigo.advertisementapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.View.VISIBLE
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Extensions {
    companion object {
        fun Context.isNetworkConnected(): Boolean {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ?: kotlin.run { false }
        }
        fun showLoading(view: View){
            view.visibility = VISIBLE
        }
         fun getCurrentDateTime(): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            return dateFormat.format(Date())
        }
    }
}