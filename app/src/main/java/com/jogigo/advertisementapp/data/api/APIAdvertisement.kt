package com.jogigo.advertisementapp.data.api

import android.content.Context
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.data.models.PropertyDetail
import com.jogigo.advertisementapp.utils.Extensions.Companion.isNetworkConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class APIAdvertisement : WSBase() {
    companion object {
        suspend fun getProperties(context: Context): List<Property> {
            if (!context.isNetworkConnected()) {
                throw Exception(context.getString(R.string.error_internet))
            }

            return withContext(Dispatchers.IO) {
                val call = getRetrofit()
                    .create(ApiService::class.java)
                    .getAdvertisements()
                    .execute()

                val response = call.body()
                if (response != null) {
                    checkStatus(context, call.code())
                    response
                } else {
                    throw Exception(context.getString(R.string.error_server))
                }
            }
        }

        suspend fun getPropertyDetail(context: Context): PropertyDetail {
            if (!context.isNetworkConnected()) {
                throw Exception(context.getString(R.string.error_internet))
            }

            return withContext(Dispatchers.IO) {
                try {
                    val call = getRetrofit()
                        .create(ApiService::class.java)
                        .getAdvertisementDetail()
                        .execute()

                    val response = call.body()

                    if (response != null) {
                        checkStatus(context, call.code())
                        response
                    } else {
                        throw Exception(context.getString(R.string.error_server))
                    }
                } catch (e: Exception) {
                    throw Exception(context.getString(R.string.error_unknown))
                }
            }
        }
    }
}